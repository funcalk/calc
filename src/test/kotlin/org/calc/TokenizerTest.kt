package org.calc

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class TokenizerTest {
  @Test
  fun `nextToken should return a token for single digit number`() {
    val tokenizer = Tokenizer("1")

    assertThat(tokenizer.nextToken()).isEqualTo("1")
  }

  @Test
  fun `nextToken should return a token for multiple digits number`() {
    val tokenizer = Tokenizer("12")

    assertThat(tokenizer.nextToken()).isEqualTo("12")
  }

  @Test
  fun `nextToken should return a token for fractional number`() {
    val tokenizer = Tokenizer("1.2")

    assertThat(tokenizer.nextToken()).isEqualTo("1.2")
  }

  @Test
  fun `nextToken should returned null for an empty string`() {
    val tokenizer = Tokenizer("")

    assertThat(tokenizer.nextToken()).isNull()
  }

  @Test
  fun `nextToken should return null for a blank string`() {
    val tokenizer = Tokenizer("   ")

    assertThat(tokenizer.nextToken()).isNull()
  }

  @Test
  fun `nextToken should throw IllegalArgumentException for an unknown input`() {
    val tokenizer = Tokenizer("#")

    assertThrows<IllegalArgumentException> { tokenizer.nextToken() }
  }

  @Test
  fun `nextToken should throw IllegalArgumentException for a number using comma`() {
    val tokenizer = Tokenizer("1,2")

    assertThrows<IllegalArgumentException> { tokenizer.nextToken() }
  }

  @Test
  fun `nextToken should throw IllegalArgumentException for a number without integer part`() {
    val tokenizer = Tokenizer("1.")

    assertThrows<IllegalArgumentException> { tokenizer.nextToken() }
  }

  @Test
  fun `nextToken should throw IllegalArgumentException for a number without fractional part`() {
    val tokenizer = Tokenizer(".2")

    assertThrows<IllegalArgumentException> { tokenizer.nextToken() }
  }
}