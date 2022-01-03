package io.github.funcalk.repl

import io.github.funcalk.expression.Parser

class StatementParser(private val input: String) {
  private val statementRegex = Regex("""(?:\s*(\w+)\s*=)?(.*)""")

  fun parse(): Statement {
    val result = statementRegex.matchEntire(input) ?: throw RuntimeException("Input \"$input\" cannot be matched, error in regex?")
    val (variable, expressionInput) = result.destructured
    val expression = Parser(expressionInput).parse()
    return if (variable.isEmpty()) {
      ExpressionValue(expression)
    } else {
      Assignment(variable, expression)
    }
  }
}