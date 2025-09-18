package com.ghiloufi.kata.application.validation;

import com.ghiloufi.kata.application.error.TechnicalError;

public final class Require {

  private Require() {}

  public static <T> T nonNull(T object, TechnicalError error) {
    if (object == null) {
      throw error.toException();
    }
    return object;
  }
}
