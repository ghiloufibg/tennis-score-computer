package com.ghiloufi.kata.testutil.helpers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class OutputCapture {

  private final ByteArrayOutputStream outputStream;
  private final PrintStream originalOut;

  public OutputCapture() {
    this.outputStream = new ByteArrayOutputStream();
    this.originalOut = System.out;
    System.setOut(new PrintStream(outputStream));
  }

  public String getCapturedOutput() {
    return outputStream.toString();
  }

  public String[] getCapturedOutputLines() {
    return getCapturedOutput().split(System.lineSeparator());
  }

  public void restore() {
    System.setOut(originalOut);
  }
}
