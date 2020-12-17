package actors

import akka.actor.Actor
import akka.actor.ActorRef
import scala.collection.mutable.Map
import play.api.libs.json._

object PadletManager {
    case class NewUser(user: ActorRef)
    case class Message(json: JsValue)
}

class PadletManager extends Actor {

    private var rooms: Map[String, ActorRef] = Map()

    import PadletManager._

    def receive = {
        case NewUser(user) => 
            println("New connection")
            user ! PadletActor.SendMessage(Json.obj(
                "type" -> "rooms",
                "data" -> Json.toJson(rooms.keys)
            ))
        case Message(json) => 
            println("received json")    
            //for (user <- users) user ! PadletActor.SendMessage(json)
        case m => println("Unhandled message in RoomManager: " + m)
    }
} 