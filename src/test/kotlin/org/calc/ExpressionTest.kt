package org.calc

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
}