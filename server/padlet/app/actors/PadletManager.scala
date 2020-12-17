package actors

import akka.actor.Actor
import akka.actor.ActorRef

object PadletManager {
    case class NewUser(user: ActorRef)
    case class Message(msg: String)
}

class PadletManager extends Actor {

    private var users = List.empty[ActorRef]

    import PadletManager._

    def receive = {
        case NewUser(user) => 
            println("New user")
            users ::= user
        case Message(msg) => 
            println(msg)
            for (user <- users) user ! PadletActor.SendMessage(msg)
        case m => println("Unhandled message in RoomManager: " + m)
    }
} 