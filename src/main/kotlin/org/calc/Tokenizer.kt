package org.calc

class Tokenizer(private val input: String) {
  fun nextToken(): String? {
    if (input.isBlank()) {
      return null
    }
    if (numberRegex.matches(input)) {
      return input
    }
    throw IllegalArgumentException("Unknown input: $input")
  }

  companion object {
    val numberRegex = Regex("""\d+(?:\.\d+)?""")
  }
}