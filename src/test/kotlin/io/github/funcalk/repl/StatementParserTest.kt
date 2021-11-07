package io.github.funcalk.repl

import org.assertj.core.api.Assertions.assertThat
import io.github.funcalk.expression.Number
import org.junit.jupiter.api.Test

internal class StatementParserTest {
  @Test
  fun expression() {
    val statement = StatementParser("1").parse()

    assertThat(statement).isEqualTo(ExpressionValue(Number(1.0)))
  }

  @Test
  fun assignment() {
    val statement = StatementParser("x = 1").parse()

    assertThat(statement).isEqualTo(Assignment("x", Number(1.0)))
  }
}