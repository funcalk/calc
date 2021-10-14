package org.funcalk

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ExpressionTest {
  @Test
  fun sum() {
    val sum = Plus(Number(1.0), Plus(Number(2.0), Number(3.0)))

    assertThat(sum.calculate()).isEqualTo(6.0)
  }

  @Test
  fun sub() {
    val sub = Minus(Number(6.0), Minus(Number(6.0), Number(3.0)))

    assertThat(sub.calculate()).isEqualTo(3.0)
  }

  @Test
  fun mult() {
    val sub = Mult(Number(2.0), Mult(Number(6.0), Number(3.0)))

    assertThat(sub.calculate()).isEqualTo(36.0)
  }

  @Test
  fun div() {
    val sub = Div(Number(8.0), Div(Number(6.0), Number(3.0)))

    assertThat(sub.calculate()).isEqualTo(4.0)
  }

  @Test
  fun `unary minus`() {
    val sub = UnaryMinus(Number(4.0))

    assertThat(sub.calculate()).isEqualTo(-4.0)
  }

  @Test
  fun `unary plus`() {
    val sub = UnaryPlus(Number(4.0))

    assertThat(sub.calculate()).isEqualTo(4.0)
  }

  @Test
  fun `power`() {
    val sub = Power(Number(3.0), Number(2.0))

    assertThat(sub.calculate()).isEqualTo(9.0)
  }
}