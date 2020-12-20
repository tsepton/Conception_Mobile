package actors

import akka.actor.Actor
import akka.actor.ActorRef
import scala.collection.mutable.Map
import scala.util.{Try, Success, Failure}
import play.api.libs.json._

object EventHandler {
  case class NewUser(user: ActorRef)
  case class Message(json: JsValue, user: ActorRef)
}

class EventHandler extends Actor {

  private var rooms: Map[Int, ActorRef] = Map()
  private var usersRooms: Map[ActorRef, Int] = Map()

  import EventHandler._

  def createRoom(user: ActorRef): Unit = {
    val roomId = if (rooms.isEmpty) 1 else rooms.keys.max + 1
    rooms += (roomId -> context.actorOf(
      Room.props(roomId),
      roomId.toString()
    ))
    rooms(roomId) ! Room.AddUser(user)
    usersRooms += (user -> roomId)
  }

  def joinRoom(user: ActorRef, json: JsValue): Unit = {
    (json \ "id").asOpt[Int] match {
      case Some(id) if rooms.keys.exists(_ == id) =>
        rooms(id) ! Room.AddUser(user)
        usersRooms += (user -> id)
      case Some(id) =>
        user ! User.SendMessage(
          Json.obj(
            "event" -> "notification",
            "text" -> "Room does not exist"
          )
        )
      case _ =>
    }
  }

  def newCard(user: ActorRef): Unit = {
    val roomId: Int = (usersRooms(user))
    val room: ActorRef = rooms(roomId)
    room ! Room.NewCard()
  }

  def receive: PartialFunction[Any, Unit] = {
    case NewUser(user) => println("New connection")
    case Message(json, user) =>
      (json \ "event").asOpt[String] match {

        // Room related
        case Some("create_room") => createRoom(user)
        case Some("join_room")   => joinRoom(user, json)
        case Some("leave_room")  => user ! User.LeaveRoom()

        // Room's cards related
        case Some("new_card")    => newCard(user)
        case Some("delete_card") =>
        case Some("update_card") =>
        case event               => println("Unhandled event received " + event)
        // TODO
      }

    //for (user <- users) user ! User.SendMessage(json)
    case message => println("Unhandled message in Room: " + message)
  }
}
