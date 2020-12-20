package actors

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import play.api.libs.json._

object Room {

  def props(id: Int): Props = Props(new Room(id))

  case class AddUser(user: ActorRef)
  case class RemoveUser(user: ActorRef)
  case class Message(json: JsValue)
  case class NewCard()
  case class DeleteCard(id: Int)
}

class Room(id: Int) extends Actor {

  // FIXME : Use mutable lists
  private var users = List.empty[ActorRef]
  private var cards = List.empty[Card]

  import Room._

  def receive: PartialFunction[Any, Unit] = {
    case AddUser(user)    => users ::= user
    case RemoveUser(user) => users = users.filter(_ != user)
    case NewCard()        => AddCard()
    case DeleteCard(id)   => cards = cards.filter(_ != id)

    case Message(json) =>
    case message       => println("Unhandled message in Room: " + message)
  }

  def AddCard(): Unit = {
    val newCard = new Card(cards.length)
    cards ::= newCard
    users.foreach(user =>
      user ! User.SendMessage(
        Json.obj("event" -> "created_card", "card" -> newCard.toJson())
      )
    )
  }
}

class Card(id: Int) {
  private var title: String = "Title"
  private var body: String = "This the default card body..."

  def editTitle(title: String): Unit =
    this.title = title

  def editBody(body: String): Unit =
    this.body = body

  def toJson(): JsValue =
    Json.obj("id" -> this.id, "title" -> this.title, "body" -> this.body)
}
