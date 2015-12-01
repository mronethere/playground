package playground.core

import akka.actor.Actor
import akka.pattern.ask
import playground.session.SignIn
import spray.http.HttpCookie
import spray.routing._

import scala.util.Success

class Router extends Actor with Index {
  def actorRefFactory = context
  def receive = runRoute(indexRoute)
}

trait Index extends HttpService {
  val indexRoute =
    path("") {
      getFromResource("app/index.html")
    }
}

trait Login {
  self: Actor with HttpService =>

  import Application.session
  import this.context.dispatcher

  val loginRoute =
    path("signin") {
      post {
        entity(as[String]) { name =>
          onComplete((session ? SignIn(name)).mapTo[String]) {
            case Success(id) => setCookie(HttpCookie("id", content = id)) { complete(_) }
            case _ => deleteCookie("id") { complete(_) }
          }
        }
      }
    }
}
