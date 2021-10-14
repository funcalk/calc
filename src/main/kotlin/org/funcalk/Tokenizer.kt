package org.funcalk

private val tokenRegex = Regex("""(?i)\d+(?:\.\d+)?|\+|-|\*|/|\(|\)|pi|e|\^""")

class Tokenizer(private val input: String) : Sequence<String> {
  override fun iterator(): Iterator<String> = TokenIterator(input)
}

private class TokenIterator(private val input: String) : AbstractIterator<String>() {
  private val matchResultIterator = tokenRegex.findAll(input).iterator()
  private var previousMatchLast: Int = -1
  override fun computeNext() {
    if (matchResultIterator.hasNext()) {
      val matchResult = matchResultIterator.next()
      val skippedInputRange = (previousMatchLast + 1) until matchResult.range.first
      val skippedInput = input.substring(skippedInputRange)
      if (skippedInput.isBlank()) {
        setNext(matchResult.value)
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