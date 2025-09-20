package com.ghiloufi.kata.common.error;

public abstract class GameRuntimeException extends RuntimeException {

  private final Enum<?> errorType;

  protected GameRuntimeException(Enum<?> errorType, String message) {
    super(message);
    this.errorType = errorType;
  }

  protected final Enum<?> getRawErrorType() {
    return errorType;
  }
}
