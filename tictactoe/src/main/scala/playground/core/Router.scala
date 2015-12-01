package playground.core

import akka.actor.Actor
import akka.pattern.ask
import playground.session.SignIn
import spray.http.HttpCookie
import spray.routing._

import scala.util.Success

class Router extends Actor with IndexAndResources with Login {
  def actorRefFactory = context
  def receive = runRoute(indexAndResourcesRoute ~ loginRoute)
}

trait IndexAndResources extends HttpService {
  val indexAndResourcesRoute =
    path("") {
      getFromResource("app/index.html")
    } ~ pathPrefix("app") {
      getFromResourceDirectory("app")
    } ~ pathPrefix("bower_components") {
      getFromResourceDirectory("bower_components")
    }
}

trait Login {
  self: Actor with HttpService =>

  import Application._
  import this.context.dispatcher

  val loginRoute =
    path("authenticate") {
      post {
        entity(as[String]) { name =>
          onComplete((session ? SignIn(name)).mapTo[String]) {
            case Success(id) => setCookie(HttpCookie("id", id)) { complete("done") }
            case _ => deleteCookie("id") { complete("") }
          }
        }
      }
    }
}
