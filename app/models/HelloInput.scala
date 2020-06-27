package models

case class HelloInput(greeting: String, name: String, age: Int) {
  require(greeting.trim.nonEmpty)
  require(name.trim.nonEmpty)
  require(age > 0)
  require(age < 130)
}