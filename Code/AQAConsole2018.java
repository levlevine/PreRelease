package words.with.aqa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class for use in the 2018 AQA exam for students using Java.
 *
 * write and writeLine are the exact equivalents of print and println.
 * 
 */

class Console {

  public Console() {
  }// end of constructor 

  /**
   *
   * @return  the line entered from the console as a string
   */
  public static String readLine() {
    String input = "";
    InputStreamReader isr = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(isr);
    try {

      input = br.readLine();

    } catch (IOException ioe) {
      println("IO Error reading from command line.");
    } // end try/catch
    return input;

  } // end method readLine

  /**
   * The parameter is printed to the console
   * @param o   the object to be printed to the console.
   */
   public static void print(Object o) {
    String output = String.valueOf(o);
    System.out.print(output);
  } // end method print

  /**
   * A new line character is output to the console.
   */
   public static void println() {
    println("");
  } // end method println with no parameter

  /**
   * The parameter is printed to the console followed by a new line character
   * @param o   the object to be printed to the console.
   */
   public static void println(Object o) {
    String output = String.valueOf(o);
    System.out.println(output);
  } // end method println

  /**
   * The parameter is printed to the console
   * @param o   the object to be printed to the console.
   */
   public static void write(Object o) {
    print(o);
  } // end method write

  /**
   * A new line character is output to the console.
   */
   public static void writeLine() {
    println();
  } // end method writeLine with no parameter

  /**
   * The parameter is printed to the console followed by a new line character
   * @param o   the object to be printed to the console.
   */
   public static void writeLine(Object o) {
    println(o);
  } // end method writeLine

  /**
   * printf is used to output args in the format given.
   * See public PrintStream printf(...)
   * @param format  the format for the output
   * @param args
   */public static void printf(String format, Object args) {
    System.out.printf(format, args);
  } // end method printf

} // end class AQAConsole2017
