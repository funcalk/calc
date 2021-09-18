package org.calc

class Parser(tokenizer: Tokenizer) {
  private val tokenIterator = tokenizer.iterator()

  fun parseExpression(): Expression {
    var currentExpression = parseNumber()
    while (tokenIterator.hasNext()) {
      currentExpression = when (val token = tokenIterator.next()) {
        "+" -> Plus(currentExpression, parseNumber())
        "-" -> Minus(currentExpression, parseNumber())
        else -> throw IllegalArgumentException("Unexpected token: $token")
      }
    }
    return currentExpression
  }

  private fun parseNumber(): Expression {
    if (tokenIterator.hasNext()) {
      return Number(tokenIterator.next().toDouble())
    }
    throw IllegalArgumentException("Expected a number")
  }
}