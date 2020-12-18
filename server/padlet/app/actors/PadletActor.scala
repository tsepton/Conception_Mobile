package actors

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import play.api.libs.json._

object PadletActor {

    def props(out: ActorRef, manager: ActorRef) = Props(new PadletActor(out, manager))

    case class SendMessage(json: JsValue)
    case class ChangeRoom(roomRef: ActorRef)
    case class LeaveRoom()
}

class PadletActor(out: ActorRef, manager: ActorRef) extends Actor {

    manager ! PadletManager.NewUser(self)

    private var room: ActorRef = _ 

    import PadletActor._
    
    def receive = {
        case json: JsValue => 
            manager ! PadletManager.Message(json, self)
        case SendMessage(json) => out ! json
        case ChangeRoom(roomRef) => room = roomRef
        case LeaveRoom() => room ! RoomManager.RemoveUser(self)
        case m => println("Unhandled message in PadletActor: " + m)
    }

    override def postStop() = {
        println("actor stopped")
        room ! RoomManager.RemoveUser(self)
    }
}