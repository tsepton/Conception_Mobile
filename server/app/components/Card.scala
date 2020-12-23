package components

import play.api.libs.json._

class Card(
    id: Int,
    title: String = "Default title",
    body: String = "This the default card body..."
) {

  def getId: Int = id
  def getTitle: String = title
  def getBody: String = body
  def toJson: JsValue =
    Json.obj("id" -> this.id, "title" -> this.title, "body" -> this.body)
}
