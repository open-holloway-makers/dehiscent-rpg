package core;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.StringTokenizer;


public class IO {

  public final static int PARA_WIDTH = 70;
  public final static int BOX_WIDTH = 60;

  // Decisions are always lower case
  public static String getDecision(String s) {
    print(s);
    Scanner in = new Scanner(System.in);
    return in.nextLine().toLowerCase().trim();
  }

  public static boolean getAffirmative(String s) {
    return IO.getDecision(s).startsWith("y");
  }

  public static double getNumberWithinRange(String s, int min, int max, boolean suppress) {
    String decision = IO.getDecision(s);
    double d = Double.NEGATIVE_INFINITY;
    try {
      d = Double.parseDouble(decision);
    } catch (NumberFormatException e) {
      // Don't set d, let d carry through to next error handling block
    }
    if (d == Double.NEGATIVE_INFINITY || d < min || d > max) {
      if (!suppress) IO.printf("Please enter a number between %d and %d\n", min, max);
      return Double.NEGATIVE_INFINITY;
    }
    return d;
  }

  public static double getNumberWithinRange(String s, int min, int max) {
    return getNumberWithinRange(s, min, max, false);
  }

  // Just because there's gonna be a lot of printing...
  public static void print(Object s) {
    System.out.print(s);
  }

  public static void println() {
    System.out.println();
  }

  public static void println(Object s) {
    System.out.println(s);
  }

  public static void printf(String format, Object... arguments) {
    System.out.format(format, arguments);
  }

  public static void debug(Object s) {
    System.out.println(s);
  }

  public static String repeatString(String str, int n) {
    StringBuffer outputBuffer = new StringBuffer();
    for (int i = 0; i < n; i++) {
      outputBuffer.append(str);
    }
    return outputBuffer.toString();
  }

  public static String formatBanner(int maxLineLength) {
    return "+" + IO.repeatString("-", maxLineLength - 2) + "+\n";
  }

  public static String formatOpposite(int maxLineLength, Object str1, Object str2) {
    int len = (maxLineLength - 4)/ 2;
    String formatString = String.format("| %%-%ds%%%ds |\n", len, len);
    return String.format(formatString, str1, str2);
  }

  public static String formatAsBox(String input, int maxLineLength, boolean bordered) {
    if (bordered) {
      maxLineLength = maxLineLength - 4;
    }
    StringTokenizer tok = new StringTokenizer(input, " ");
    StringBuilder output = new StringBuilder(input.length());
    if (bordered) {
      output.append(formatBanner(maxLineLength + 4));
      output.append("| ");
    } else {
      output.append("\n");
    }
    String newline = (bordered) ? " |\n| " : "\n";
    int lineLen = 0;
    while (tok.hasMoreTokens()) {
      String word = tok.nextToken();

      while(word.length() > maxLineLength){
        output.append(word.substring(0, maxLineLength - lineLen) +
                IO.repeatString(" ", maxLineLength - lineLen) + newline);
        word = word.substring(maxLineLength-lineLen);
        lineLen = 0;
      }
      if (lineLen + word.length() > maxLineLength) {

        output.append(IO.repeatString(" ", maxLineLength - lineLen) + newline);
        lineLen = 0;
      }
      output.append(word + " ");
      lineLen += word.length() + 1;
    }
    if (bordered) {
      output.append(IO.repeatString(" ", maxLineLength - lineLen) + " |\n");
      output.append(formatBanner(maxLineLength + 4));
    } else {
      output.append("\n\n");
    }
    return output.toString();
  }

  public static PrintStream getNullPrintStream() {
    return new PrintStream(new OutputStream() {
      @Override
      public void write(int b) throws IOException {
      }
    });
  }
}


