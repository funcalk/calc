package org.calc

class Parser(private val tokenizer: Tokenizer) {
  fun parse(): Expression {
    val i = tokenizer.iterator()
    val token = if (i.hasNext()) i.next() else throw IllegalArgumentException("Empty input")
    return Number(token.toDouble())
  }
}