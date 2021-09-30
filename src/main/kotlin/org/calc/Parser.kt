package org.calc

class Parser(tokenizer: Tokenizer) {
  private val tokenIterator = tokenizer.iterator()
  private var currentToken: String? = null

  fun parseExpression(): Expression {
    var currentExpression = parseTerm()
    while (currentToken != null) {
      currentExpression = when (currentToken) {
        "+" -> Plus(currentExpression, parseTerm())
        "-" -> Minus(currentExpression, parseTerm())
        else -> throw IllegalArgumentException("Unexpected token: $currentToken")
      }
    }
    return currentExpression
  }

  private fun parseTerm(): Expression {
    var currentExpression = parseFactor()
    while (currentToken != null) {
      currentExpression = when (currentToken) {
        "*" -> Mult(currentExpression, parseFactor())
        "/" -> Div(currentExpression, parseFactor())
        else -> break
      }
    }
    return currentExpression
  }

  private fun parseFactor(): Expression {
    readNextToken()
    return when (currentToken) {
      "-" -> {
        readNextToken()
        UnaryMinus(parseNumber())
      }
      "+" -> {
        readNextToken()
        UnaryPlus(parseNumber())
      }
      else -> parseNumber()
    }
  }

  private fun parseNumber(): Expression {
    currentToken?.let { token ->
      readNextToken()
      token.toDoubleOrNull()?.let { return Number(it) } ?: throw IllegalArgumentException("Expected a number: $token")
    }
    throw IllegalArgumentException("Expected a number")
  }

  private fun readNextToken() {
    currentToken = if (tokenIterator.hasNext()) tokenIterator.next() else null
  }
}