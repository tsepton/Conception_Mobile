package actors

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import play.api.libs.json._

import bacht._
import components.Card

object Room {

  def props(id: Int): Props = Props(new Room(id))

  case class AddUser(user: ActorRef)
  case class RemoveUser(user: ActorRef)
  case class Message(json: JsValue)
  case class NewCard()
  case class DeleteCard(id: Int)
  case class UpdateCard(card: Card)
}

class Room(id: Int) extends Actor {

  private var users = List.empty[ActorRef]
  private val store = new BachTStore();
  private val simul = new BachTSimul(store);

  import Room._

  def receive: PartialFunction[Any, Unit] = {
    case AddUser(user)    => addUser(user)
    case RemoveUser(user) => users = users.filter(_ != user)
    case NewCard()        => newCard()
    case DeleteCard(id)   => deleteCard(id)
    case UpdateCard(card) => updateCard(card)

    case Message(json) =>
    case message       => println("Unhandled message in Room: " + message)
  }

  def addUser(user: ActorRef): Unit = {
    users ::= user
    user ! User.SendMessage(
      Json.obj(
        "event" -> "enter_room",
        "room" -> Json.toJson(id),
        "cards" -> store.getValues.map(card => card.toJson)
      )
    )
    user ! User.ChangeRoom(self)
  }

  def newCard(): Unit = {
    val id: Int = store.length
    simul.run(s"tell(${id})")
    users.foreach(user =>
      user ! User.SendMessage(
        Json.obj("event" -> "created_card", "card" -> new Card(id).toJson)
      )
    )
  }

  def deleteCard(id: Int): Unit = {
    simul.run(s"get(${id})")
    users.foreach(user =>
      user ! User.SendMessage(
        Json.obj("event" -> "deleted_card", "id" -> id)
      )
    )
  }

  def updateCard(target: Card): Unit = {
    simul.run(s"get(${target.getId});tell(${target.getId}, ${target.getTitle}, ${target.getBody})")
    users.foreach(user =>
      user ! User.SendMessage(
        Json.obj("event" -> "modified_card", "card" -> target.toJson)
      )
    )
  }
}
