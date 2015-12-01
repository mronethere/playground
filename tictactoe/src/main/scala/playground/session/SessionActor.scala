package playground.session

import akka.actor.{ActorLogging, Actor}
import scala.collection.mutable

/**
  * This is not like common session object, more like the simplest version of caching without any erasing.
  */
class SessionActor extends Actor with ActorLogging {

  val users = mutable.Map.empty[String, UserData]

  def incrementVictoryCount(userName: String, selfVictory: Boolean) = users.get(userName) match {
    case Some(userData @ UserData(selfCount, aiCount)) =>
      users.update(userName, if (selfVictory) UserData(selfCount + 1, aiCount) else UserData(selfCount, aiCount + 1))
      log.info(s"Cannot update victory count. User $userName does not exist")
    case _ => log.warning(s"Cannot update victory count. User $userName does not exist")
  }

  def receive = {
    case msg: SessionMessage => msg match {

      case SignIn(userName) =>
        if(!users.isDefinedAt(userName)) users + (userName -> UserData(0, 0))
        log.info(s"User $userName successfully signed in")

      case SelfVictory(userName) => incrementVictoryCount(userName, selfVictory = true)
      case AIVictory(userName) => incrementVictoryCount(userName, selfVictory = false)
    }
    case msg: _ => log.warning(s"unknown message: $msg")
  }
}

case class UserData(selfWin: Int, aiWin: Int)

sealed trait SessionMessage
case class SignIn(userName: String) extends SessionMessage
case class SelfVictory(userName: String) extends SessionMessage
case class AIVictory(userName: String) extends SessionMessage