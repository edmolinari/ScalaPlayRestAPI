package models

import models.ResponseStatus.ResponseStatus

case class HelloMessageResponse(greetingMessage: String, age: Int)
case class ResponseMessage(status: ResponseStatus, content: HelloMessageResponse)

