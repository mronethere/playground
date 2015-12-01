package playground.core

import akka.actor.Actor
import spray.routing._

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
