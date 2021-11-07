package io.github.funcalk.repl

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import io.github.funcalk.expression.Number
import io.github.funcalk.expression.Plus
import io.github.funcalk.expression.Var
import org.junit.jupiter.api.Test

internal class StatementTest {
  @Test
  fun `assignment should create variable`() {
    val vars = mutableMapOf<String, Double>()
    val statement = Assignment("x", Plus(Number(1.0), Number(2.0)))

    assertThat(statement.evaluate(vars)).isEqualTo(3.0)
    assertThat(vars).contains(entry("x", 3.0))
  }

  @Test
  fun `assignment should change variable`() {
    val vars = mutableMapOf("x" to 1.0)
    val statement = Assignment("x", Plus(Number(1.0), Number(2.0)))

    assertThat(statement.evaluate(vars)).isEqualTo(3.0)
    assertThat(vars).contains(entry("x", 3.0))
  }

  @Test
  fun `assignment should be able to reference an existing variable`() {
    val vars = mutableMapOf("x" to 1.0)
    val statement = Assignment("x", Plus(Var("x"), Number(2.0)))

    assertThat(statement.evaluate(vars)).isEqualTo(3.0)
    assertThat(vars).contains(entry("x", 3.0))
  }
}