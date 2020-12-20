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

  def createRoom(user: ActorRef): Unit = {
    val roomId = if (rooms.isEmpty) 1 else rooms.keys.max + 1
    rooms += (roomId -> context.actorOf(
      RoomManager.props(roomId),
      roomId.toString()
    ))
    rooms(roomId) ! RoomManager.AddUser(user)
    user ! PadletActor.SendMessage(
      Json.obj("event" -> "enter_room", "room" -> Json.toJson(roomId))
    )
    user ! PadletActor.ChangeRoom(rooms(roomId))
  }

  def joinRoom(user: ActorRef, json: JsValue): Unit = {
    (json \ "id").asOpt[Int] match {
      case Some(id) if rooms.keys.exists(_ == id) =>
        rooms(id) ! RoomManager.AddUser(user)
        user ! PadletActor.SendMessage(
          Json.obj("event" -> "enter_room", "room" -> Json.toJson(id))
        )
        user ! PadletActor.ChangeRoom(rooms(id))
      case Some(id) =>
        user ! PadletActor.SendMessage(
          Json.obj(
            "event" -> "error",
            "message" -> "Room does not exist"
          )
        )
      case _ =>
    }
  }

  def receive: PartialFunction[Any, Unit] = {
    case NewUser(user) => println("New connection")
    case Message(json, user) =>
      (json \ "event").asOpt[String] match {

        case Some("create_room") => createRoom(user)
        case Some("join_room")   => joinRoom(user, json)
        case Some("leave_room")  => user ! PadletActor.LeaveRoom()
        case _                   => println("Unhandled event received")
        // TODO
      }

    //for (user <- users) user ! PadletActor.SendMessage(json)
    case message => println("Unhandled message in RoomManager: " + message)
  }
}
