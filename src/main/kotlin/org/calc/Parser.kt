package org.calc

class Parser(private val tokenizer: Tokenizer) {
  fun parse(): Expression {
    val i = tokenizer.iterator()
    val token = if (i.hasNext()) i.next() else throw IllegalArgumentException("Empty input")
    if (i.hasNext()) throw IllegalArgumentException("Unexpected token: ${i.next()}")
    return Number(token.toDouble())
  }
}