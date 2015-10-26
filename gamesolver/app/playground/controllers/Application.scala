package playground.controllers

import play.api.mvc._

import playground._

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("The game"))
  }

}
