package actors

import akka.actor.Actor
import akka.actor.ActorRef
import play.api.libs.json._

object RoomManager {
    case class NewUser(user: ActorRef)
    case class Message(json: JsValue)
}

class RoomManager extends Actor {

    private var users = List.empty[ActorRef]

    import RoomManager._

    def receive = {
        case NewUser(user) => 
            println("New user")
            users ::= user
        case Message(json) => 
            //println(json)
            //for (user <- users) user ! PadletActor.SendMessage(json)
        case m => println("Unhandled message in RoomManager: " + m)
    }
} 