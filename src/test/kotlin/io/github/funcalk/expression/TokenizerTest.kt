package io.github.funcalk.expression

import org.assertj.core.api.Assertions.assertThat
import io.github.funcalk.expression.TokenType.DIVIDE
import io.github.funcalk.expression.TokenType.LEFT_PARENTHESIS
import io.github.funcalk.expression.TokenType.MINUS
import io.github.funcalk.expression.TokenType.MULTIPLY
import io.github.funcalk.expression.TokenType.NUMBER
import io.github.funcalk.expression.TokenType.PLUS
import io.github.funcalk.expression.TokenType.POWER
import io.github.funcalk.expression.TokenType.RIGHT_PARENTHESIS
import io.github.funcalk.expression.TokenType.SYMBOL
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class TokenizerTest {
  @Test
  fun `tokenizer should return a token for single digit number`() {
    val tokenizer = Tokenizer("1")

    val token = Token("1")
    assertThat(tokenizer.toList()).containsExactly(token)
    assertThat(token.type).isEqualTo(NUMBER)
  }

  @Test
  fun `tokenizer should return a token for multiple digits number`() {
    val tokenizer = Tokenizer("12")

    val token = Token("12")
    assertThat(tokenizer.toList()).containsExactly(token)
    assertThat(token.type).isEqualTo(NUMBER)
  }

  @Test
  fun `tokenizer should return a token for fractional number`() {
    val tokenizer = Tokenizer("1.2")

    val token = Token("1.2")
    assertThat(tokenizer.toList()).containsExactly(token)
    assertThat(token.type).isEqualTo(NUMBER)
  }

  @Test
  fun `tokenizer should be empty for an empty string`() {
    val tokenizer = Tokenizer("")

    assertThat(tokenizer.toList()).isEmpty()
  }

  @Test
  fun `tokenizer should be empty for a blank string`() {
    val tokenizer = Tokenizer("   ")

    assertThat(tokenizer.toList()).isEmpty()
  }

  @Test
  fun `tokenizer should throw IllegalArgumentException for an unknown input`() {
    val tokenizer = Tokenizer("#")

    assertThrows<IllegalArgumentException> { tokenizer.toList() }
  }

  @Test
  fun `tokenizer should throw IllegalArgumentException for a number using comma`() {
    val tokenizer = Tokenizer("1,2")

    assertThrows<IllegalArgumentException> { tokenizer.toList() }
  }

  @Test
  fun `tokenizer should throw IllegalArgumentException for a number without fractional part`() {
    val tokenizer = Tokenizer("1.")

    assertThrows<IllegalArgumentException> { tokenizer.toList() }
  }

  @Test
  fun `tokenizer should throw IllegalArgumentException for a number without integer part`() {
    val tokenizer = Tokenizer(".2")

    assertThrows<IllegalArgumentException> { tokenizer.toList() }
  }

  @Test
  fun `tokenizer should return a token for a plus`() {
    val tokenizer = Tokenizer("+")

    val token = Token("+")
    assertThat(tokenizer.toList()).containsExactly(token)
    assertThat(token.type).isEqualTo(PLUS)
  }

  @Test
  fun `tokenizer should return a token for a minus`() {
    val tokenizer = Tokenizer("-")

    val token = Token("-")
    assertThat(tokenizer.toList()).containsExactly(token)
    assertThat(token.type).isEqualTo(MINUS)
  }

  @Test
  fun `tokenizer should return tokens for an expression (+)`() {
    val tokenizer = Tokenizer(" 1 + 2 ")

    assertThat(tokenizer.toList().map { it.value }).containsExactly("1", "+", "2")
  }

  @Test
  fun `tokenizer should return tokens for an expression (-)`() {
    val tokenizer = Tokenizer(" 1 - 2 ")

    assertThat(tokenizer.toList().map { it.value }).containsExactly("1", "-", "2")
  }

  @Test
  fun `tokenizer should return a token for a mult`() {
    val tokenizer = Tokenizer("*")

    val token = Token("*")
    assertThat(tokenizer.toList()).containsExactly(token)
    assertThat(token.type).isEqualTo(MULTIPLY)
  }

  @Test
  fun `tokenizer should return a token for a div`() {
    val tokenizer = Tokenizer("/")

    val token = Token("/")
    assertThat(tokenizer.toList()).containsExactly(token)
    assertThat(token.type).isEqualTo(DIVIDE)
  }

  @Test
  fun `tokenizer should return token ^`() {
    val tokenizer = Tokenizer("^")

    val token = Token("^")
    assertThat(tokenizer.toList()).containsExactly(token)
    assertThat(token.type).isEqualTo(POWER)
  }

  @Test
  fun `tokenizer should return tokens for left parenthesis`() {
    val tokenizer = Tokenizer("(")

    val token = Token("(")
    assertThat(tokenizer.toList()).containsExactly(token)
    assertThat(token.type).isEqualTo(LEFT_PARENTHESIS)
  }

  @Test
  fun `tokenizer should return tokens for right parenthesis`() {
    val tokenizer = Tokenizer(")")

    val token = Token(")")
    assertThat(tokenizer.toList()).containsExactly(token)
    assertThat(token.type).isEqualTo(RIGHT_PARENTHESIS)
  }

  @Test
  fun `tokenizer should return symbol token`() {
    val tokenizer = Tokenizer("pi")

    val token = Token("pi")
    assertThat(tokenizer.toList()).containsExactly(token)
    assertThat(token.type).isEqualTo(SYMBOL)
  }

  @Test
  fun `tokenizer should return symbol token preserving case`() {
    val tokenizer = Tokenizer("PI")

    val token = Token("PI")
    assertThat(tokenizer.toList()).containsExactly(token)
    assertThat(token.type).isEqualTo(SYMBOL)
  }

  @Test
  fun `tokenizer should return symbol token with digits`() {
    val tokenizer = Tokenizer("x2")

    val token = Token("x2")
    assertThat(tokenizer.toList()).containsExactly(token)
    assertThat(token.type).isEqualTo(SYMBOL)
  }
}