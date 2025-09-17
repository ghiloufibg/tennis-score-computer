package com.ghiloufi.kata.testutil.base;

import com.ghiloufi.kata.testutil.builders.TennisTestBuilder;
import org.junit.jupiter.api.BeforeEach;

public abstract class TennisTestBase {

  protected TennisTestBuilder.TestEnvironment testEnvironment;

  @BeforeEach
  void setUp() {
    testEnvironment = TennisTestBuilder.createTestEnvironment();
  }
}
