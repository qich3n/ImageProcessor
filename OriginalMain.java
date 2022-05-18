import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import controller.ImageController;
import controller.ConsoleControllerImpl;

/**
 * This is the main class of the Image Processing Application Program.
 */
public class OriginalMain {
  /**
   * Main method for executing the java program.
   * @param args the argument that the user inputs.
   * @throws IOException throws an exception if I/O during read/write fails.
   */
  public static void main(String[] args) throws IOException {
    if (args.length > 1 && args[0].equals("-file")) {
      try {
        File runFile = new File( args[1]);
        InputStream input = new FileInputStream(runFile);
        ImageController con = new ConsoleControllerImpl(input);
        con.imageProcessor();
      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("The file given in the command line args was not found");
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      ImageController con = new ConsoleControllerImpl();
      con.imageProcessor();
    }
  }
}
