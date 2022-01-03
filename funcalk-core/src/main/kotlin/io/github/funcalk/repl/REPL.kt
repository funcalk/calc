@file:JvmName("REPL")

package io.github.funcalk.repl

import java.text.DecimalFormat

private val numberFormat = DecimalFormat("0.#########")

fun main() {
  val vars = mutableMapOf<String, Double>()
  var input = readInput()
  while (input != null) {
    try {
      val statement = StatementParser(input).parse()
      println(numberFormat.format(statement.evaluate(vars)))
    } catch (e: IllegalArgumentException) {
      println("Error: ${e.message}")
    }
    input = readInput()
  }
}

private fun readInput(): String? {
  print("Enter a statement: ")
  return readLine()
}