package actors

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import play.api.libs.json._

object RoomManager {
    
    def props(id: Int) = Props(new RoomManager(id)) 

    case class AddUser(user: ActorRef)
    case class RemoveUser(user: ActorRef)
    case class Message(json: JsValue)
}

class RoomManager(id: Int) extends Actor {

    private var users = List.empty[ActorRef]

    import RoomManager._

    def receive = {
        case AddUser(user) => 
            println("Add user to room " + id + ": " + user)
            users ::= user
            println(users)
        case RemoveUser(user) => 
            println("Remove user from room " + id + ": " + user)
            users = users.filter(_!=user)
            println(users)
        case Message(json) => 
            // TODO: Handle messages for cards on padlet
        case m => println("Unhandled message in RoomManager: " + m)
    }
} 