package playground.session

import akka.actor.{ActorLogging, Actor}

class SessionActor extends Actor with ActorLogging {
  def receive = {
    case SignIn(userName) => ???
  }
}

case class User(name: String, id: String, selfWin: Int)

trait SessionMessage
case class SignIn(userName: String) extends SessionMessage