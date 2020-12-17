package actors

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import play.api.libs.json._

object RoomManager {
    
    def props(id: Int) = Props(new RoomManager(id)) 

    case class AddUser(user: ActorRef)
    case class Message(json: JsValue)
}

class RoomManager(id: Int) extends Actor {

    private var users = List.empty[ActorRef]

    import RoomManager._

    def receive = {
        case AddUser(user) => 
            println("Add user to room " + id + ": " + user)
            users ::= user
        case Message(json) => 
            //println(json)
            //for (user <- users) user ! PadletActor.SendMessage(json)
        case m => println("Unhandled message in RoomManager: " + m)
    }
} 