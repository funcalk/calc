package org.calc

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertNotNull

internal class ParserTest {
  @Test
  fun `parse a floating-point number`() {
    val parser = Parser(Tokenizer("1.1"))

    val expression = assertNotNull(parser.parse())

    assertThat(expression).isInstanceOf(Number::class.java)
    assertThat(expression.calculate()).isEqualTo(1.1)
  }

  @Test
  fun `parse empty input`() {
    val parser = Parser(Tokenizer(""))

    assertThrows<IllegalArgumentException>{ parser.parse() }
  }
  
  @Test
  fun `parse input with an extra token`() {
    val parser = Parser(Tokenizer("1 2"))

    assertThrows<IllegalArgumentException>{ parser.parse() }
  }
}