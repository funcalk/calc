package io.github.funcalk.expression

import io.github.funcalk.expression.TokenType.DIVIDE
import io.github.funcalk.expression.TokenType.LEFT_PARENTHESIS
import io.github.funcalk.expression.TokenType.MINUS
import io.github.funcalk.expression.TokenType.MULTIPLY
import io.github.funcalk.expression.TokenType.PLUS
import io.github.funcalk.expression.TokenType.POWER
import io.github.funcalk.expression.TokenType.RIGHT_PARENTHESIS
import io.github.funcalk.expression.TokenType.SYMBOL
import java.lang.Math.E
import java.lang.Math.PI

class Parser(input: String) {
  private val tokenIterator = Tokenizer(input).iterator()
  private var currentToken: Token? = null

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
      currentExpression = when (currentToken?.type) {
        PLUS -> Plus(currentExpression, parseTerm())
        MINUS -> Minus(currentExpression, parseTerm())
        else -> break
      }
    }
    return currentExpression
  }

  private fun parseTerm(): Expression {
    var currentExpression = parseFactor()
    while (currentToken != null) {
      currentExpression = when (currentToken?.type) {
        MULTIPLY -> Mult(currentExpression, parseFactor())
        DIVIDE -> Div(currentExpression, parseFactor())
        else -> break
      }
    }
    return currentExpression
  }

  private fun parseFactor(): Expression {
    readNextToken()
    return when (currentToken?.type) {
      PLUS -> {
        readNextToken()
        UnaryPlus(parsePower())
      }
      MINUS -> {
        readNextToken()
        UnaryMinus(parsePower())
      }
      else -> parsePower()
    }
  }

  private fun parsePower(): Expression {
    val left = parsePrimary()
    return when (currentToken?.type) {
      POWER -> {
        readNextToken()
        Power(left, parsePrimary())
      }
      else -> left
    }
  }

  private fun parsePrimary(): Expression {
    val token = currentToken ?: throw IllegalArgumentException("Expected a number, a constant or (")
    return when (token.type) {
      LEFT_PARENTHESIS -> {
        val expression = parseExpression()
        if (currentToken?.type != RIGHT_PARENTHESIS) {
          throw IllegalArgumentException("Expected )")
        }
        readNextToken()
        expression
      }
      SYMBOL -> {
        val expression = when (token.value.lowercase()) {
          "pi" -> Number(PI)
          "e" -> Number(E)
          else -> Var(token.value)
        }
        readNextToken()
        expression
      }
      else -> {
        readNextToken()
        token.value.toDoubleOrNull()?.let { Number(it) } ?: throw IllegalArgumentException("Expected a number: $token")
      }
    }
  }

  private fun readNextToken() {
    currentToken = if (tokenIterator.hasNext()) tokenIterator.next() else null
  }
}