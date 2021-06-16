package org.calc

sealed class Expression {
  abstract fun calculate(): Double
}

class Number(private val value: Double) : Expression() {
  override fun calculate(): Double = value
}