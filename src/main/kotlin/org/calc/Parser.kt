package org.calc

class Parser(tokenizer: Tokenizer) {
  private val tokenIterator = tokenizer.iterator()

  fun parseExpression(): Expression {
    val left = parseNumber()
    return if (tokenIterator.hasNext()) {
      val token = tokenIterator.next()
      if (token == "+") {
        val right = parseExpression()
        Plus(left, right)
      } else {
        throw IllegalArgumentException("Unexpected token: $token")
      }
    } else {
      left
    }
  }

  private fun parseNumber(): Expression {
    if (tokenIterator.hasNext()) {
      return Number(tokenIterator.next().toDouble())
    }
    throw IllegalArgumentException("Expected a number")
  }
}