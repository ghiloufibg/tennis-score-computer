package com.ghiloufi.kata.application.error;

public final class TechnicalException extends RuntimeException {

  private final TechnicalError error;

  public TechnicalException(TechnicalError error, String message) {
    super(message);
    this.error = error;
  }

  public TechnicalError getError() {
    return error;
  }
}
