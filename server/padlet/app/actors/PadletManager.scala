package actors

import akka.actor.Actor
import akka.actor.ActorRef
import scala.collection.mutable.Map
import play.api.libs.json._
import scala.util.{Try, Success, Failure}

object PadletManager {
  case class NewUser(user: ActorRef)
  case class Message(json: JsValue, user: ActorRef)
}

class PadletManager extends Actor {

  private var rooms: Map[Int, ActorRef] = Map()

  import PadletManager._

  def receive = {
    case NewUser(user) =>
      println("New connection")
    case Message(json, user) =>
      println("received event")
      (json \ "event").asOpt[String] match {

        // Handle creating room
        case Some("create_room") =>
          val roomId = {
            if (rooms.isEmpty)
              1
            else
              rooms.keys.max + 1
          }
          rooms += (roomId -> context.actorOf(
            RoomManager.props(roomId),
            roomId.toString()
          ))
          rooms(roomId) ! RoomManager.AddUser(user)
          user ! PadletActor.SendMessage(
            Json.obj(
              "event" -> "enter_room",
              "room" -> Json.toJson(roomId)
            )
          )
          user ! PadletActor.ChangeRoom(rooms(roomId))

        // Handle user joining room 
        case Some("join_room") =>
            (json \ "id").asOpt[Int] match {
              case Some(id) if rooms.values.exists(_ == id) => {
                rooms(id) ! RoomManager.AddUser(user)
                user ! PadletActor.SendMessage(
                  Json.obj(
                    "event" -> "enter_room",
                    "room" -> Json.toJson(id)
                  )
                )
                user ! PadletActor.ChangeRoom(rooms(id))
              }
              case None =>
              case _ => println("Room doesn't exist")
            }

        // Handle user leave room
        case Some("leave_room") => 
              user ! PadletActor.LeaveRoom()

        // Handle other cases
        case None =>
      }

    //for (user <- users) user ! PadletActor.SendMessage(json)
    case m => println("Unhandled message in RoomManager: " + m)
  }
}
