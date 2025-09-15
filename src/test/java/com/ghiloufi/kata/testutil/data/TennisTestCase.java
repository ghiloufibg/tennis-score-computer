package com.ghiloufi.kata.testutil.data;

import com.ghiloufi.kata.domain.GameState;

public record TennisTestCase(
    String input,
    String description,
    GameState expectedState,
    String expectedLastMessage,
    Integer expectedHistorySize) {}
