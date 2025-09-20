package com.ghiloufi.kata.domain.error;

import com.ghiloufi.kata.common.error.ExceptionFactory;

public enum GameError implements ExceptionFactory<GameException> {
  INVALID_PLAYER("Invalid player: %c. Only 'A' or 'B' are allowed."),
  INVALID_PLAYER_AT_POSITION("Invalid player: %c at position %d. Only 'A' or 'B' are allowed."),
  NOTATION_EMPTY("Match notation cannot be null or empty"),
  NOTATION_TOO_LONG("Match notation too long: %d points. Maximum allowed: %d"),
  NEGATIVE_POINTS("Points cannot be negative: %d");

  private final String messageTemplate;

  GameError(String messageTemplate) {
    this.messageTemplate = messageTemplate;
  }

  @Override
  public GameException toException(Object... args) {
    String message = args.length > 0 ? String.format(messageTemplate, args) : messageTemplate;
    return new GameException(this, message);
  }
}
