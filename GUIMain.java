import java.io.IOException;

import controller.GUIControllerImpl;
import controller.ImageController;

/**
 * This is the main class of the GUI portion of the Image Processing Program.
 */
public class GUIMain {

  /**
   * Main method for executing the GUI portion of the program.
   * @param args the argument that the user inputs.
   * @throws IOException throws an exception if I/O during read/write fails.
   */
  public static void main(String[] args) throws IOException {
    ImageController con = new GUIControllerImpl();
    con.imageProcessor();
  }

}