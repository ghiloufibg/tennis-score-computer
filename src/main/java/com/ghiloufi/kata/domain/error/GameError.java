package com.ghiloufi.kata.domain.error;

public enum GameError {
  INVALID_PLAYER("Invalid player: %c. Only 'A' or 'B' are allowed."),
  INVALID_PLAYER_AT_POSITION("Invalid player: %c at position %d. Only 'A' or 'B' are allowed."),
  NOTATION_EMPTY("Match notation cannot be null or empty"),
  NOTATION_TOO_LONG("Match notation too long: %d points. Maximum allowed: %d");

  private final String messageTemplate;

  GameError(String messageTemplate) {
    this.messageTemplate = messageTemplate;
  }

  public GameException toException(Object... args) {
    String message = args.length > 0 ? String.format(messageTemplate, args) : messageTemplate;
    return new GameException(this, message);
  }
}
