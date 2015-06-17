package core;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IO {
  // Decisions are always lower case
  public static String getDecision() {
    return getDecision("");
  }

  public static String getDecision(String s) {
    print(s);
    Scanner in = new Scanner(System.in);
    return in.nextLine().toLowerCase();
  }

  // Just because there's gonna be a lot of printing...
  public static void print(String s) {
    System.out.print(s);
  }

  public static void println(String s) {
    System.out.println(s);
  }

  public static PrintStream getNullPrintStream() {
    return new PrintStream(new OutputStream() {
      @Override
      public void write(int b) throws IOException {
      }
    });
  }
}


