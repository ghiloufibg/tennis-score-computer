package com.ghiloufi.kata.input;

import com.ghiloufi.kata.validator.InputValidator;
import java.util.Iterator;

public final class GameSequenceProvider {

  private static final int MAX_INPUT_LENGTH = 10000;

  private GameSequenceProvider() {}

  public static Iterator<Character> fromString(String input) {
    final String sanitizedInput = InputValidator.sanitizeInput(input);
    InputValidator.validateGameInput(sanitizedInput);
    InputValidator.validateInputLength(sanitizedInput, MAX_INPUT_LENGTH);

    return new GameSequenceIterator(sanitizedInput);
  }
}
