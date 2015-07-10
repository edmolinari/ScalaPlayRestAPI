package controllers

import play.api.mvc._
import play.api.libs.json._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  //curl -H "Content-Type: application/json" -X POST -d '{"name":"xyz"}' http://localhost:9000/hello
  def hello = Action(parse.json) { request =>
    val json = request.body
    val nameStr = (json \ "name").asOpt[String]

    nameStr.map { name =>
      Ok(Json.obj("status" ->"OK", "message" -> ("Hello "+ name )))
    }.getOrElse {
      BadRequest(Json.obj("status" -> "KO", "message" -> "Missing parameter [name]"))
    }
  }


  //curl -H "Content-Type: application/json" -X POST -d '{"name":"xyz"}' http://localhost:9000/hello-async
  def helloAsync = Action.async(parse.json) { request =>
    val json = request.body
    val nameStr = (json \ "name").asOpt[String]

    val futureResponse = Future(nameStr)

    futureResponse.map { name =>
      Ok(Json.obj("status" ->"OK", "message" -> ("Hello "+ name.get )))
    }
  }


}
