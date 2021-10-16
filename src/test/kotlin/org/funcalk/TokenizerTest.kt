package org.funcalk

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class TokenizerTest {
  @Test
  fun `tokenizer should return a token for single digit number`() {
    val tokenizer = Tokenizer("1")

    assertThat(tokenizer.toList()).containsExactly("1")
  }

  @Test
  fun `tokenizer should return a token for multiple digits number`() {
    val tokenizer = Tokenizer("12")

    assertThat(tokenizer.toList()).containsExactly("12")
  }

  @Test
  fun `tokenizer should return a token for fractional number`() {
    val tokenizer = Tokenizer("1.2")

    assertThat(tokenizer.toList()).containsExactly("1.2")
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

    assertThat(tokenizer.toList()).containsExactly("+")
  }

  @Test
  fun `tokenizer should return tokens for an expression (+)`() {
    val tokenizer = Tokenizer(" 1 + 2 ")

    assertThat(tokenizer.toList()).containsExactly("1", "+", "2")
  }

  @Test
  fun `tokenizer should return tokens for an expression (-)`() {
    val tokenizer = Tokenizer(" 1 - 2 ")

    assertThat(tokenizer.toList()).containsExactly("1", "-", "2")
  }

  @Test
  fun `tokenizer should return a token for a minus`() {
    val tokenizer = Tokenizer("-")

    assertThat(tokenizer.toList()).containsExactly("-")
  }

  @Test
  fun `tokenizer should return a token for a mult`() {
    val tokenizer = Tokenizer("*")

    assertThat(tokenizer.toList()).containsExactly("*")
  }

  @Test
  fun `tokenizer should return a token for a div`() {
    val tokenizer = Tokenizer("/")

    assertThat(tokenizer.toList()).containsExactly("/")
  }

  @Test
  fun `tokenizer should return tokens for parentheses`() {
    val tokenizer = Tokenizer("()")

    assertThat(tokenizer.toList()).containsExactly("(", ")")
  }

  @Test
  fun `tokenizer should return token pi for pi`() {
    val tokenizer = Tokenizer("pi")

    assertThat(tokenizer.toList()).containsExactly("pi")
  }

  @Test
  fun `tokenizer should return token e for e`() {
    val tokenizer = Tokenizer("e")

    assertThat(tokenizer.toList()).containsExactly("e")
  }

  @Test
  fun `tokenizer should return token pi for PI`() {
    val tokenizer = Tokenizer("PI")

    assertThat(tokenizer.toList()).containsExactly("PI")
  }

  @Test
  fun `tokenizer should return token e for E`() {
    val tokenizer = Tokenizer("E")

    assertThat(tokenizer.toList()).containsExactly("E")
  }

  @Test
  fun `tokenizer should return token ^`() {
    val tokenizer = Tokenizer("^")

    assertThat(tokenizer.toList()).containsExactly("^")
  }
}