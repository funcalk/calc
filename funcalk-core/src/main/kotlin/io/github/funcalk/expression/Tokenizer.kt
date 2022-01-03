package io.github.funcalk.expression

internal class Tokenizer(private val input: String) : Sequence<Token> {
  override fun iterator(): Iterator<Token> = TokenIterator(input)
}

internal data class Token(val value: String) {
  val type: TokenType = TokenType.values().first { it.regex.matches(value) }
}

internal enum class TokenType(val regex: Regex) {
  NUMBER(Regex("""\d+(?:\.\d+)?""")),
  SYMBOL(Regex("""\w+""")),
  LEFT_PARENTHESIS(Regex("""\(""")),
  RIGHT_PARENTHESIS(Regex("""\)""")),
  PLUS(Regex("""\+""")),
  MINUS(Regex("""-""")),
  MULTIPLY(Regex("""\*""")),
  DIVIDE(Regex("""/""")),
  POWER(Regex("""\^"""))
}

private val tokenRegex = Regex(TokenType.values().joinToString("|") { it.regex.pattern })

private class TokenIterator(private val input: String) : AbstractIterator<Token>() {
  private val matchResultIterator = tokenRegex.findAll(input).iterator()
  private var previousMatchLast: Int = -1
  override fun computeNext() {
    if (matchResultIterator.hasNext()) {
      val matchResult = matchResultIterator.next()
      val skippedInputRange = (previousMatchLast + 1) until matchResult.range.first
      val skippedInput = input.substring(skippedInputRange)
      if (skippedInput.isBlank()) {
        setNext(Token(matchResult.value))
        previousMatchLast = matchResult.range.last
      } else {
        failOnUnknownInput(skippedInputRange)
      }
    } else {
      val leftInputRange = (previousMatchLast + 1) until input.length
      val leftInput = input.substring(leftInputRange)
      if (leftInput.isBlank()) {
        done()
      } else {
        failOnUnknownInput(leftInputRange)
      }
    }
  }

  private fun failOnUnknownInput(unknownInputRange: IntRange) {
    throw IllegalArgumentException("Unknown input at [$unknownInputRange]: ${input.substring(unknownInputRange)}")
  }
}