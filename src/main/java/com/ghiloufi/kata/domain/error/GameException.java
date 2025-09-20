package com.ghiloufi.kata.domain.error;

import com.ghiloufi.kata.common.error.GameRuntimeException;

public final class GameException extends GameRuntimeException {

  public GameException(GameError error, String message) {
    super(error, message);
  }

  private GameError getErrorType() {
    return (GameError) getRawErrorType();
  }

  public GameError getError() {
    return getErrorType();
  }
}
