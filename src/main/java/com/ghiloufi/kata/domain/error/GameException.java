package com.ghiloufi.kata.domain.error;

public final class GameException extends RuntimeException {

  private final GameError error;

  public GameException(GameError error, String message) {
    super(message);
    this.error = error;
  }

  public GameError getError() {
    return error;
  }
}
