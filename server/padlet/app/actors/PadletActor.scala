package actors

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props

object PadletActor {

    def props(out: ActorRef, manager: ActorRef) = Props(new PadletActor(out, manager))

    case class SendMessage(msg: String)
}

class PadletActor(out: ActorRef, manager: ActorRef) extends Actor {

    manager ! PadletManager.NewUser(self)

    import PadletActor._
    
    def receive = {
        case s: String => manager ! PadletManager.Message(s)
        case SendMessage(msg) => out ! msg
        case m => println("Unhandled message in PadletActor: " + m)
    }
}