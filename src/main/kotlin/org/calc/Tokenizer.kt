package org.calc

private val numberRegex = Regex("""\d+(?:\.\d+)?""")

class Tokenizer(private val input: String) : Sequence<String> {
  override fun iterator(): Iterator<String> = TokenIterator(input)
}

private class TokenIterator(private val input: String) : AbstractIterator<String>() {
  private val tokenIterator = numberRegex.findAll(input).iterator()
  private var previousMatchLast: Int = -1
  override fun computeNext() {
    if (tokenIterator.hasNext()) {
      val result = tokenIterator.next()
      val skippedInputRange = previousMatchLast + 1 until result.range.first
      val skippedInput = input.substring(skippedInputRange)
      if (skippedInput.isBlank()) {
        setNext(result.value)
        previousMatchLast = result.range.last
      } else {
        failOnUnknownInput(skippedInputRange)
      }
    } else {
      val leftInputRange = previousMatchLast + 1 until input.length
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