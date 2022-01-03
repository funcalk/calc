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
import java.lang.Math.asin
import java.lang.Math.cos
import kotlin.Double.Companion.NaN
import kotlin.Double.Companion.POSITIVE_INFINITY

private val CONSTANTS = mapOf(
  "pi" to Number(PI),
  "e" to Number(E),
  "nan" to Number(NaN),
  "inf" to Number(POSITIVE_INFINITY)
)

private val FUNCTIONS = mapOf(
  "sqrt" to Math::sqrt,
  "sin" to Math::sin,
  "cos" to Math::cos,
  "tan" to Math::tan,
  "asin" to Math::asin,
  "acos" to Math::acos,
  "atan" to Math::atan,
  "ln" to Math::log,
  "log10" to Math::log10,
  "cbrt" to Math::cbrt,
  "abs" to Math::abs
)

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
        val name = token.value
        readNextToken()
        if (currentToken?.type == LEFT_PARENTHESIS) {
          val argument = parseExpression()
          if (currentToken?.type != RIGHT_PARENTHESIS) {
            throw IllegalArgumentException("Expected )")
          }
          readNextToken()
          FunCall(FUNCTIONS[name] ?: throw IllegalArgumentException("Unknown function: $name"), argument)
        } else {
          CONSTANTS[name.lowercase()] ?: Var(name)
        }
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