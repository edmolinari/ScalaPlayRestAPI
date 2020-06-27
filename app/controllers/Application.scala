package controllers

import models._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future

class Application
  extends Controller
  with ResponseJsonFormats{


  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  //curl -H "Content-Type: application/json" -X POST -d '{"name":"xyz"}' http://localhost:9000/hello
  def hello = Action(parse.json) { request =>
    val json = request.body
    val inputName = (json \ "name").asOpt[String]

    inputName.map { name =>
      Ok(Json.obj("status" ->"OK", "message" -> ("Hello "+ name )))
    }.getOrElse {
      BadRequest(Json.obj("status" -> "KO", "message" -> "Missing parameter [name]"))
    }
  }


  //curl -H "Content-Type: application/json" -X POST -d '{"name":"xyz"}' http://localhost:9000/hello-async
  def helloAsync = Action.async(parse.json) { request =>
    val json = request.body
    val inputName = (json \ "name").asOpt[String]

    val futureResponse = Future(inputName)

    futureResponse.map { name =>
      Ok(Json.obj("status" ->"OK", "message" -> ("Hello "+ name.get )))
    }
  }

  //curl -H "Content-Type: application/json" -X POST -d '{"greeting":"Hi", "name": "xyz", "age": 25}' http://localhost:9000/hello-v2
  def helloV2 = Action(parse.json) { request =>
    val json = request.body

    json.validate[HelloInput] match {
      case s: JsSuccess[HelloInput] => {
        val input = s.get
        val helloMessage = HelloMessage(HelloText(input.greeting), Name(input.name)) //new BuildHelloMessage(HelloText(input.greeting), Name(input.name))
        val helloMessageResponse = HelloMessageResponse(helloMessage.buildMessage, input.age)
        val response = ResponseMessage(ResponseStatus.Ok, helloMessageResponse)
        Ok(Json.toJson(response))
      }
      case e: JsError => BadRequest(Json.obj("status" -> ResponseStatus.KO, "message" -> JsError.toJson(e).toString()))
    }

  }


}
