package org.calc

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertNotNull

internal class ParserTest {
  @Test
  fun `parse a floating-point number`() {
    val parser = Parser(Tokenizer("1.1"))

    val expression = assertNotNull(parser.parseExpression())

    assertThat(expression).isInstanceOf(Number::class.java)
    assertThat(expression.calculate()).isEqualTo(1.1)
  }

  @Test
  fun `parse empty input`() {
    val parser = Parser(Tokenizer(""))

    assertThrows<IllegalArgumentException> { parser.parseExpression() }
  }

  @Test
  fun `parse input with an extra token`() {
    val parser = Parser(Tokenizer("1 2"))

    assertThrows<IllegalArgumentException> { parser.parseExpression() }
  }

  @Test
  fun `parse a sum`() {
    val parser = Parser(Tokenizer("   2    +   3    "))

    val expression = parser.parseExpression()

    assertThat(expression).isEqualTo(Plus(Number(2.0), Number(3.0)))
  }

  @Test
  fun `parse a sub`() {
    val parser = Parser(Tokenizer(" 6  -    2    -   3    "))

    val expression = parser.parseExpression()

    assertThat(expression).isEqualTo(Minus(Minus(Number(6.0), Number(2.0)), Number(3.0)))
  }

  @Test
  fun `parse a mult`() {
    val parser = Parser(Tokenizer(" 6  *    2    *   3    "))

    val expression = parser.parseExpression()

    assertThat(expression).isEqualTo(Mult(Mult(Number(6.0), Number(2.0)), Number(3.0)))
  }

  @Test
  fun `parse a div`() {
    val parser = Parser(Tokenizer("6 / 2 / 3"))

    val expression = parser.parseExpression()

    assertThat(expression).isEqualTo(Div(Div(Number(6.0), Number(2.0)), Number(3.0)))
  }
  
  @Test
  fun `operators priority`() {
    val parser = Parser(Tokenizer("4 + 8 * 6 - 2 / 3"))

    val expression = parser.parseExpression()

    assertThat(expression).isEqualTo(
      Minus(
        Plus(
          Number(4.0),
          Mult(Number(8.0), Number(6.0))
        ),
        Div(Number(2.0), Number(3.0))
      )
    )
  }
}