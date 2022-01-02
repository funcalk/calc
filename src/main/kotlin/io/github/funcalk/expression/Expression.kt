package io.github.funcalk.expression

import kotlin.math.pow

sealed class Expression {
  abstract fun calculate(vars: Map<String, Double> = emptyMap()): Double
}

data class Number(private val value: Double) : Expression() {
  override fun calculate(vars: Map<String, Double>): Double = value
}

data class Plus(
  private val left: Expression,
  private val right: Expression
) : Expression() {
  override fun calculate(vars: Map<String, Double>): Double = left.calculate(vars) + right.calculate(vars)
}

data class Minus(
  private val left: Expression,
  private val right: Expression
) : Expression() {
  override fun calculate(vars: Map<String, Double>): Double = left.calculate(vars) - right.calculate(vars)
}

data class Mult(
  private val left: Expression,
  private val right: Expression
) : Expression() {
  override fun calculate(vars: Map<String, Double>): Double = left.calculate(vars) * right.calculate(vars)
}

data class Div(
  private val left: Expression,
  private val right: Expression
) : Expression() {
  override fun calculate(vars: Map<String, Double>): Double = left.calculate(vars) / right.calculate(vars)
}

data class Power(
  private val left: Expression,
  private val right: Expression
) : Expression() {
  override fun calculate(vars: Map<String, Double>): Double = left.calculate(vars).pow(right.calculate(vars))
}

data class UnaryMinus(private val expression: Expression) : Expression() {
  override fun calculate(vars: Map<String, Double>): Double = -expression.calculate(vars)
}

data class UnaryPlus(private val expression: Expression) : Expression() {
  override fun calculate(vars: Map<String, Double>): Double = expression.calculate(vars)
}

data class Var(private val name: String) : Expression() {
  override fun calculate(vars: Map<String, Double>): Double = vars[name] ?: throw IllegalArgumentException("Unknown variable \"$name\"")
}

data class FunCall(private val function: (Double) -> Double, private val argument: Expression) : Expression() {
  override fun calculate(vars: Map<String, Double>): Double = function.invoke(argument.calculate(vars))
}