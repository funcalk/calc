package org.calc

class Parser(private val tokenizer: Tokenizer) {
  fun parse(): Expression {
    val token = tokenizer.nextToken() ?: throw IllegalArgumentException("Empty input")
    return Number(token.toDouble())
  }
}