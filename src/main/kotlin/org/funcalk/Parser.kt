package org.funcalk

import java.lang.Math.E
import java.lang.Math.PI

class Parser(tokenizer: Tokenizer) {
  private val tokenIterator = tokenizer.iterator()
  private var currentToken: String? = null

  fun parse(): Expression {
    val expression = parseExpression()
    if (currentToken != null) {
      throw IllegalArgumentException("Unexpected token: $currentToken")
    }
    return expression
  }

  private fun parseExpression(): Expression {
    var currentExpression = parseTerm()
    while (currentToken != null) {
      currentExpression = when (currentToken) {
        "+" -> Plus(currentExpression, parseTerm())
        "-" -> Minus(currentExpression, parseTerm())
        else -> break
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
        UnaryMinus(parsePower())
      }
      "+" -> {
        readNextToken()
        UnaryPlus(parsePower())
      }
      else -> parsePower()
    }
  }

  private fun parsePower(): Expression {
    val left = parsePrimary()
    return when (currentToken) {
      "^" -> {
        readNextToken()
        Power(left, parsePrimary())
      }
      else -> left
    }
  }

  private fun parsePrimary(): Expression {
    return when (val token = currentToken?.lowercase()) {
      "(" -> {
        val expression = parseExpression()
        if (currentToken != ")") {
          throw IllegalArgumentException("Expected )")
        }
        readNextToken()
        expression
      }
      "pi" -> {
        readNextToken()
        Number(PI)
      }
      "e" -> {
        readNextToken()
        Number(E)
      }
      null -> throw IllegalArgumentException("Expected a number, a constant or (")
      else -> {
        readNextToken()
        token.toDoubleOrNull()?.let { Number(it) } ?: throw IllegalArgumentException("Expected a number: $token")
      }
    }
  }

  private fun readNextToken() {
    currentToken = if (tokenIterator.hasNext()) tokenIterator.next() else null
  }
}