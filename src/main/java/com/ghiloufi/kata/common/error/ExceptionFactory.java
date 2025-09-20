package com.ghiloufi.kata.common.error;

public interface ExceptionFactory<T extends RuntimeException> {
  T toException(Object... args);
}
