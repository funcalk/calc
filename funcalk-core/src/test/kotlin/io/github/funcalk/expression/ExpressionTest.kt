package io.github.funcalk.expression

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class ExpressionTest {
  @Test
  fun sum() {
    val exp = Plus(Number(1.0), Plus(Number(2.0), Number(3.0)))

    assertThat(exp.calculate()).isEqualTo(6.0)
  }

  @Test
  fun sub() {
    val exp = Minus(Number(6.0), Minus(Number(6.0), Number(3.0)))

    assertThat(exp.calculate()).isEqualTo(3.0)
  }

  @Test
  fun mult() {
    val exp = Mult(Number(2.0), Mult(Number(6.0), Number(3.0)))

    assertThat(exp.calculate()).isEqualTo(36.0)
  }

  @Test
  fun div() {
    val exp = Div(Number(8.0), Div(Number(6.0), Number(3.0)))

    assertThat(exp.calculate()).isEqualTo(4.0)
  }

  @Test
  fun `unary minus`() {
    val exp = UnaryMinus(Number(4.0))

    assertThat(exp.calculate()).isEqualTo(-4.0)
  }

  @Test
  fun `unary plus`() {
    val exp = UnaryPlus(Number(4.0))

    assertThat(exp.calculate()).isEqualTo(4.0)
  }

  @Test
  fun power() {
    val exp = Power(Number(3.0), Number(2.0))

    assertThat(exp.calculate()).isEqualTo(9.0)
  }

  @Test
  fun variable() {
    val exp = Var("x")
    val vars = mapOf("x" to 2.0)

    assertThat(exp.calculate(vars)).isEqualTo(2.0)
  }

  @Test
  fun `unknown variable`() {
    val exp = Var("x")

    val e = assertThrows<IllegalArgumentException> { exp.calculate() }
    assertThat(e.message?.lowercase()).contains("variable", "x")
  }

  @Test
  fun `function call`() {
    val exp = FunCall(Math::sqrt, Number(4.0))

    assertThat(exp.calculate(mapOf())).isEqualTo(2.0)
  }
}