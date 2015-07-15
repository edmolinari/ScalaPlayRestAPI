package models

case class Name(name: String) extends AnyVal
case class HelloText(helloText: String) extends AnyVal

case class HelloMessage(greeting: HelloText, to: Name) {
  def buildMessage: String = {
    val helloText = greeting.helloText
    val name  = to.name
    s"$helloText $name"
  }
}