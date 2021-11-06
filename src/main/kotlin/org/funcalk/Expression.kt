package org.funcalk

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
  override fun calculate(vars: Map<String, Double>): Double = left.calculate() + right.calculate()
}

data class Minus(
  private val left: Expression,
  private val right: Expression
) : Expression() {
  override fun calculate(vars: Map<String, Double>): Double = left.calculate() - right.calculate()
}

data class Mult(
  private val left: Expression,
  private val right: Expression
) : Expression() {
  override fun calculate(vars: Map<String, Double>): Double = left.calculate() * right.calculate()
}

data class Div(
  private val left: Expression,
  private val right: Expression
) : Expression() {
  override fun calculate(vars: Map<String, Double>): Double = left.calculate() / right.calculate()
}

data class Power(
  private val left: Expression,
  private val right: Expression
) : Expression() {
  override fun calculate(vars: Map<String, Double>): Double = left.calculate().pow(right.calculate())
}

data class UnaryMinus(private val number: Expression) : Expression() {
  override fun calculate(vars: Map<String, Double>): Double = -number.calculate()
}

data class UnaryPlus(private val number: Expression) : Expression() {
  override fun calculate(vars: Map<String, Double>): Double = number.calculate()
}

data class Var(private val name: String) : Expression() {
  override fun calculate(vars: Map<String, Double>): Double = vars[name] ?: throw NoSuchElementException("Unknown variable \"$name\"")
}