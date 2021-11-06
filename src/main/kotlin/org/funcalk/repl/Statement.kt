package org.funcalk.repl

import org.funcalk.expression.Expression

sealed class Statement {
  abstract fun evaluate(vars: MutableMap<String, Double>): Double
}

data class ExpressionValue(private val expression: Expression) : Statement() {
  override fun evaluate(vars: MutableMap<String, Double>): Double = expression.calculate(vars)
}

data class Assignment(
  private val variable: String,
  private val expression: Expression
) : Statement() {
  override fun evaluate(vars: MutableMap<String, Double>): Double {
    val value = expression.calculate(vars)
    vars[variable] = value
    return value
  }
}