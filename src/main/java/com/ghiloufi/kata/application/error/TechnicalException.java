package com.ghiloufi.kata.application.error;

import com.ghiloufi.kata.common.error.GameRuntimeException;

public final class TechnicalException extends GameRuntimeException {

  public TechnicalException(TechnicalError error, String message) {
    super(error, message);
  }

  private TechnicalError getErrorType() {
    return (TechnicalError) getRawErrorType();
  }

  public TechnicalError getError() {
    return getErrorType();
  }
}
