package controllers

import play.api.mvc._
import play.api.libs.streams.ActorFlow
import javax.inject._
import akka.actor.ActorSystem
import akka.actor.Props
import akka.stream.Materializer
import actors.PadletActor
import actors.PadletManager
import play.api.libs.json._

@Singleton
class WebSocketsController @Inject() (cc: ControllerComponents)(implicit system: ActorSystem, mat: Materializer)
extends AbstractController(cc)
{

    val manager = system.actorOf(Props[PadletManager], "Manager")

    def index = Action { implicit request: Request[AnyContent] =>
        Ok(views.html.index())
    }

    def ws = WebSocket.accept[JsValue, JsValue] { request =>
        ActorFlow.actorRef { out =>
            PadletActor.props(out, manager)
        }
    }
}
