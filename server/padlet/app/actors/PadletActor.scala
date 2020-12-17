package actors

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import play.api.libs.json._

object PadletActor {

    def props(out: ActorRef, manager: ActorRef) = Props(new PadletActor(out, manager))

    case class SendMessage(json: JsValue)
}

class PadletActor(out: ActorRef, manager: ActorRef) extends Actor {

    manager ! PadletManager.NewUser(self)

    import PadletActor._
    
    def receive = {
        case json: JsValue => manager ! PadletManager.Message(json)
        case SendMessage(json) => out ! json
        case m => println("Unhandled message in PadletActor: " + m)
    }
}