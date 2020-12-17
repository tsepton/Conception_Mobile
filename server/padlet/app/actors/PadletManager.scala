package actors

import akka.actor.Actor
import akka.actor.ActorRef
import scala.collection.mutable.Map
import play.api.libs.json._

object PadletManager {
    case class NewUser(user: ActorRef)
    case class Message(json: JsValue, user: ActorRef)
}

class PadletManager extends Actor {

    private var rooms: Map[Int, ActorRef] = Map()

    import PadletManager._

    def receive = {
        case NewUser(user) => 
            println("New connection")
            user ! PadletActor.SendMessage(Json.obj(
                "event" -> "rooms",
                "data" -> Json.toJson(rooms.keys)
            ))
        case Message(json, user) => 
            println("received event")
            (json \ "event").asOpt[String] match {
                
                // Handle creating room
                case Some("create_room") => 
                    val roomId = {
                        if (rooms.isEmpty)
                            0
                        else
                            rooms.keys.max+1
                    }
                    rooms += (roomId -> context.actorOf(RoomManager.props(roomId), roomId.toString()))
                    rooms(roomId) ! RoomManager.AddUser(user)
                
                // Handle user joining room
                case Some("join_room") =>
                    println("join room")
                    (json \ "data").asOpt[Int] match {
                        case Some(i) => rooms(i) ! RoomManager.AddUser(user)
                        case None => 
                    }

                // Handle other cases
                case None => 
            }

            //for (user <- users) user ! PadletActor.SendMessage(json)
        case m => println("Unhandled message in RoomManager: " + m)
    }
} 