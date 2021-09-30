package org.calc

import java.text.DecimalFormat

private val numberFormat = DecimalFormat("0.#########")

fun main() {
  var input = readInput()
  while (input != null) {
    try {
      val expression = Parser(Tokenizer(input)).parse()
      println(numberFormat.format(expression.calculate()))
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