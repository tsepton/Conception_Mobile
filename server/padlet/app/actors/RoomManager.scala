package actors

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import play.api.libs.json._

object RoomManager {

  def props(id: Int): Props = Props(new RoomManager(id))

  case class AddUser(user: ActorRef)
  case class RemoveUser(user: ActorRef)
  case class Message(json: JsValue)
}

class RoomManager(id: Int) extends Actor {

  private var users = List.empty[ActorRef]

  import RoomManager._

  def receive: PartialFunction[Any, Unit] = {
    case AddUser(user)    => users ::= user
    case RemoveUser(user) => users = users.filter(_ != user)
    case Message(json)    =>
    case message          => println("Unhandled message in RoomManager: " + message)
  }
}
