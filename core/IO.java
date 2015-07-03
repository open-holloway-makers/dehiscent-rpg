package core;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * A collection of helper methods, wrapper methods
 * and formatting functions to ease the common need
 * to print stats, dialogues, etc.
 */
public class IO {

  /**
   * The standard length of a text paragraph.
   */
  public final static int PARA_WIDTH = 70;

  /**
   * The standard length of a bordered box
   * (for example, item descriptions).
   */
  public final static int BOX_WIDTH = 60;

  /**
   * Fetches some input from the console, usually
   * used to get a selected option from the player.
   *
   * Returned decision is always lowercase.
   *
   * @param s The leading question to ask the player.
   * @return a lowercase string which can be easily.
   * compared to some option.
   */
  public static String getDecision(String s) {
    print(s);
    Scanner in = new Scanner(System.in);
    return in.nextLine().toLowerCase().trim();
  }

  /**
   * Resolves a decision as true (if it begins with
   * y) or false (otherwise).
   *
   * @param s the leading question to ask the player
   * @return true if the player answers with "$[Y|y].*",
   * false otherwise.
   */
  public static boolean getAffirmative(String s) {
    return IO.getDecision(s).startsWith("y");
  }

  /**
   * Resolves a decision as a double.
   *
   * @param s the leading question to ask the player.
   * @param min the lower bound for the requested number.
   * @param max the upper bound for the requested number.
   * @param suppress suppresses the prompt to enter a number.
   * @return a double given by the player.
   */
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

  /**
   * Resolves a decision as a double.
   *
   * @param s the leading question to ask the player.
   * @param min the lower bound for the requested number.
   * @param max the upper bound for the requested number.
   * @return a double given by the player.
   */
  public static double getNumberWithinRange(String s, int min, int max) {
    return getNumberWithinRange(s, min, max, false);
  }

  /**
   * A wrapper function for the standard library's print
   * method which is just slightly too cumbersome and messy
   * given the amount of console printing likely to be in
   * the game.
   *
   * @param s the string to print.
   */
  public static void print(Object s) {
    System.out.print(s);
  }

  /**
   * A wrapper function for the standard library's println
   * method which is just slightly too cumbersome and messy
   * given the amount of console printing likely to be in
   * the game.
   */
  public static void println() {
    System.out.println();
  }

  /**
   * A wrapper function for the standard library's println
   * method which is just slightly too cumbersome and messy
   * given the amount of console printing likely to be in
   * the game.
   *
   * @param s the string to print.
   */
  public static void println(Object s) {
    System.out.println(s);
  }

  /**
   * A wrapper method for the standard library's 'printf'
   * method which is just slightly too cumbersome and messy
   * given the amount of console printing likely to be in
   * the game.
   *
   * @param format to use for printing (C-style).
   * @param arguments the arguments to supply to the format.
   */
  public static void printf(String format, Object... arguments) {
    System.out.format(format, arguments);
  }

  /**
   * Helper method to automatically print text formatted
   * to the appropriate width and without borders as is
   * often needed during exposition, dialogues, etc.
   *
   * @param s the text to be formatted and printed.
   */
  public static void printAsPara(String s) {
    IO.print(IO.formatAsBox(s, IO.PARA_WIDTH, false));
  }

  /**
   * A helper method to print debug output to standard
   * error. The main reason for having this method is to
   * have something consistent to search for when removing
   * debug statements from code about to go live:
   *
   * Find: "IO.debug("
   *
   * @param s the string to be output to standard error.
   */
  public static void debug(Object s) {
    System.err.println(s);
  }

  /**
   * Repeats a string n number of times, to mimic built in
   * functionality of many other languages, ("a" * 16).
   *
   * @param str the string to be repeated.
   * @param n the number of times to repeat the string.
   * @return the new string composed of n lots of str.
   */
  public static String repeatString(String str, int n) {
    StringBuffer outputBuffer = new StringBuffer();
    for (int i = 0; i < n; i++) {
      outputBuffer.append(str);
    }
    return outputBuffer.toString();
  }

  /**
   * Return a nicely formatted 'banner' or divider which
   * is of consistent style across all menus.
   *
   * @param maxLineLength the width of the banner
   * @return an ASCII banner
   */
  public static String formatBanner(int maxLineLength) {
    return "+" + IO.repeatString("-", maxLineLength - 2) + "+\n";
  }

  /**
   * Formats a line of values as if they are columns in a
   * table. The width of the table will fit to the maxLineLength
   * provided and the number of columns is equal to the object
   * arguments provided.
   *
   * @param maxLineLength the width of the line.
   * @param arguments the arguments (columns) to display.
   * @return a formatted string containing the arguments as columns.
   */
  public static String formatColumns(int maxLineLength, Object... arguments) {
    return formatColumns(maxLineLength, true, true, arguments);
  }

  /**
   * Formats a line of values as if they are columns in a
   * table. The width of the table will fit to the maxLineLength
   * provided and the number of columns is equal to the object
   * arguments provided.
   *
   * @param maxLineLength the width of the line.
   * @param alignLeft align the content of the middle columns to the left.
   * @param bordered print a border either side of the row.
   * @param arguments the arguments (columns) to display.
   * @return a formatted string containing the arguments as columns.
   */
  public static String formatColumns(int maxLineLength, boolean alignLeft, boolean bordered, Object... arguments) {
    int len, rem;
    StringBuilder formatString = new StringBuilder();
    if (bordered) {
      maxLineLength -= 4;
    }
    len = maxLineLength / arguments.length;
    rem = maxLineLength % arguments.length;
    if (!alignLeft) {
      if (bordered) {
        formatString.append(String.format("| %%-%ds", len + rem));
      } else {
        formatString.append(String.format("%%-%ds", len + rem));
      }
    } else {
      if (bordered) {
        formatString.append(String.format("| ", len + rem));
      }
    }
    for (int i = 0; i < arguments.length - 1; i++) {
      if (alignLeft) {
        formatString.append(String.format("%%-%ds", len));
      } else {
        formatString.append(String.format("%%%ds", len));
      }
    }
    if (alignLeft) {
      if (bordered) {
        formatString.append(String.format("%%%ds |\n", len + rem));
      } else {
        formatString.append(String.format("%%%ds\n", len + rem));
      }
    } else {
      if (bordered) {
        formatString.append(String.format(" |\n", len + rem));
      } else {
        formatString.append(String.format("\n", len + rem));
      }
    }
    return String.format(formatString.toString(), arguments);
  }

  /**
   * Formats some text as a block of text, inserting newlines
   * automatically to try and ensure each line fits within the
   * maxLineLength.
   *
   * @param input the text to be formatted.
   * @param maxLineLength the width of the box.
   * @param bordered whether the box should be bordered.
   * @return the formatted string.
   */
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

      while (word.length() > maxLineLength) {
        output.append(word.substring(0, maxLineLength - lineLen) +
                IO.repeatString(" ", maxLineLength - lineLen) + newline);
        word = word.substring(maxLineLength - lineLen);
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

  /**
   * Returns a 'null print stream' which writes all
   * output to nowhere. Can be used to suppress console
   * output but remember to save the original System.out
   * and restore it when you're done.
   *
   * @return a null print stream.
   */
  public static PrintStream getNullPrintStream() {
    return new PrintStream(new OutputStream() {
      @Override
      public void write(int b) throws IOException {
      }
    });
  }
}


