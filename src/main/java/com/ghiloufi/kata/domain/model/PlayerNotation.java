package com.ghiloufi.kata.domain.model;

public enum PlayerNotation {
  A,
  B;

  public static boolean isValidPlayerChar(char c) {
    return c == 'A' || c == 'B';
  }
}
