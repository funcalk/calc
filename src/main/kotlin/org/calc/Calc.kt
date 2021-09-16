package org.calc

fun main() {
  var input = readInput()
  while (input != null) {
    try {
      val expression = Parser(Tokenizer(input)).parse()
      println(expression.calculate())
    } catch (e: IllegalArgumentException) {
      println("Error: ${e.message}")
    }
    input = readInput()
  }
}

private fun readInput(): String? {
  print("Enter an expression: ")
  return readLine()
}