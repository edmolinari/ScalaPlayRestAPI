package controllers

import models._
import play.api.libs.json.Json

trait ResponseJsonFormats {
  implicit val helloInputFormat = Json.format[HelloInput]

  implicit val responseStatusFormat = EnumUtils.enumFormat(ResponseStatus)
  implicit val helloMessageResponseFormat = Json.format[HelloMessageResponse]
  implicit val responseMessageFormat = Json.format[ResponseMessage]
}
