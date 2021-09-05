package org.calc

private val numberRegex = Regex("""\d+(?:\.\d+)?""")

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
}