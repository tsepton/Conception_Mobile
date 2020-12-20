package actors

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import play.api.libs.json._
import scala.util.{Try, Success, Failure}

object PadletActor {

  def props(out: ActorRef, manager: ActorRef) =
    Props(new PadletActor(out, manager))

  case class SendMessage(json: JsValue)
  case class ChangeRoom(roomRef: ActorRef)
  case class LeaveRoom()
}

class PadletActor(out: ActorRef, manager: ActorRef) extends Actor {

  manager ! PadletManager.NewUser(self)

  private var room: ActorRef = _

  import PadletActor._

  def receive = {
    case json: JsValue       => manager ! PadletManager.Message(json, self)
    case SendMessage(json)   => out ! json
    case ChangeRoom(roomRef) => room = roomRef
    case LeaveRoom()         => room ! RoomManager.RemoveUser(self)
    case message             => println("Unhandled message in PadletActor: " + message)
  }

  override def postStop(): Unit =
    Try(room ! RoomManager.RemoveUser(self)) match {
      case Success(_) => println("User disconnected and left a room")
      case Failure(_) => println("User disconnected")
    }
}
