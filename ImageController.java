package controller;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import model.ImageModel;

/**
 * Represents the ImageController Interface and contains methods which opens the processor, loads
 * the image, and saves the image.
 */
public interface ImageController {

  /**
   * Method that starts the controller.
   * @throws IOException if an unexpected input is taken in.
   */
  void imageProcessor() throws IOException;

  /**
   * Loads the image file.
   * @param file Represents the file that is to be loaded.
   * @return the image in a color array.
   */
  Color[][] loadImage(File file);

  /**
   * Saves the image.
   * @param file Represents the file that is to be saved.
   * @param mod Represents the edits made on the file.
   */
  void saveImage(File file, ImageModel mod);
}
