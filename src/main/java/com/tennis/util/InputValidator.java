package com.tennis.util;

/** Utility class for validating tennis game inputs */
public final class InputValidator {

  private InputValidator() {
    // Private constructor to prevent instantiation
    throw new UnsupportedOperationException("Utility class cannot be instantiated");
  }

  /**
   * Validates that the input string contains only valid tennis ball characters
   *
   * @param input The input string to validate
   * @throws IllegalArgumentException if input is invalid
   */
  public static void validateGameInput(String input) {
    if (input == null) {
      throw new IllegalArgumentException("Input cannot be null");
    }

    if (input.isEmpty()) {
      throw new IllegalArgumentException("Input cannot be empty");
    }

    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);
      if (c != 'A' && c != 'B') {
        throw new IllegalArgumentException(
            String.format(
                "Invalid character: %c at position %d. Only 'A' or 'B' are allowed.", c, i));
      }
    }
  }

  /**
   * Checks if a character represents a valid player
   *
   * @param player The character to check
   * @return true if valid ('A' or 'B'), false otherwise
   */
  public static boolean isValidPlayer(char player) {
    return player == 'A' || player == 'B';
  }

  /**
   * Sanitizes input by removing any whitespace and converting to uppercase
   *
   * @param input The raw input string
   * @return Sanitized input string
   */
  public static String sanitizeInput(String input) {
    if (input == null) {
      return null;
    }

    return input.replaceAll("\\s+", "").toUpperCase();
  }

  /**
   * Validates that the input length is reasonable for a tennis game
   *
   * @param input The input string to check
   * @param maxLength Maximum allowed length
   * @throws IllegalArgumentException if input is too long
   */
  public static void validateInputLength(String input, int maxLength) {
    if (input != null && input.length() > maxLength) {
      throw new IllegalArgumentException(
          String.format(
              "Input too long: %d characters. Maximum allowed: %d", input.length(), maxLength));
    }
  }
}
