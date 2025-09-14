package com.ghiloufi.kata.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class InputValidatorTest {

  @Test
  @DisplayName("Valid inputs should pass validation")
  void testValidInputs() {
    assertDoesNotThrow(() -> InputValidator.validateGameInput("A"));
    assertDoesNotThrow(() -> InputValidator.validateGameInput("B"));
    assertDoesNotThrow(() -> InputValidator.validateGameInput("AB"));
    assertDoesNotThrow(() -> InputValidator.validateGameInput("AAAA"));
    assertDoesNotThrow(() -> InputValidator.validateGameInput("BBBB"));
    assertDoesNotThrow(() -> InputValidator.validateGameInput("ABABAA"));
    assertDoesNotThrow(() -> InputValidator.validateGameInput("AAABBBABABAB"));
  }

  @Test
  @DisplayName("Null input should throw exception")
  void testNullInput() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> InputValidator.validateGameInput(null));
    assertEquals("Input cannot be null", exception.getMessage());
  }

  @Test
  @DisplayName("Empty input should throw exception")
  void testEmptyInput() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> InputValidator.validateGameInput(""));
    assertEquals("Input cannot be empty", exception.getMessage());
  }

  @ParameterizedTest
  @DisplayName("Invalid characters should throw exception")
  @ValueSource(strings = {"C", "X", "1", "a", "b", "AB1", "AXB", "AA BB"})
  void testInvalidCharacters(String input) {
    assertThrows(IllegalArgumentException.class, () -> InputValidator.validateGameInput(input));
  }

  @Test
  @DisplayName("Invalid character exception should include position")
  void testInvalidCharacterPosition() {
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class, () -> InputValidator.validateGameInput("AAXBB"));
    assertTrue(exception.getMessage().contains("position 2"));
    assertTrue(exception.getMessage().contains("Invalid character: X"));
  }

  @Test
  @DisplayName("Valid player characters should return true")
  void testValidPlayerCharacters() {
    assertTrue(InputValidator.isValidPlayer('A'));
    assertTrue(InputValidator.isValidPlayer('B'));
  }

  @Test
  @DisplayName("Invalid player characters should return false")
  void testInvalidPlayerCharacters() {
    assertFalse(InputValidator.isValidPlayer('C'));
    assertFalse(InputValidator.isValidPlayer('a'));
    assertFalse(InputValidator.isValidPlayer('1'));
    assertFalse(InputValidator.isValidPlayer(' '));
  }

  @Test
  @DisplayName("Input length validation should work")
  void testInputLengthValidation() {
    // Valid lengths should not throw
    assertDoesNotThrow(() -> InputValidator.validateInputLength("AB", 10));
    assertDoesNotThrow(() -> InputValidator.validateInputLength("AAAA", 4));
    assertDoesNotThrow(() -> InputValidator.validateInputLength(null, 10));

    // Too long input should throw
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class, () -> InputValidator.validateInputLength("AAAAA", 4));
    assertTrue(exception.getMessage().contains("Input too long: 5 characters"));
    assertTrue(exception.getMessage().contains("Maximum allowed: 4"));
  }
}
