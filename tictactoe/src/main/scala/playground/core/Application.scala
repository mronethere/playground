package playground.core

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import playground.session.SessionActor
import spray.can.Http

import scala.concurrent.duration._

object Application extends App {

  implicit val system = ActorSystem("tic-tac-toe")

  val service = system.actorOf(Props[Router], "app-service")
  val session = system.actorOf(Props[SessionActor], "session")

  implicit val timeout = Timeout(5.seconds)
  IO(Http) ? Http.Bind(service, interface = "localhost", port = 8080)
}
