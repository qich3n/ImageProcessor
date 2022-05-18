package model;

import java.awt.Color;

/**
 * The ImageModel interface has all the methods that allow the image processing application to work.
 */
public interface ImageModel {

  /**
   * Provides the red composition of the pixel.
   *
   * @return the red composition of the pixel.
   */
  ImageModel redComp();

  /**
   * Provides the green composition of the pixel.
   *
   * @return the green composition of the pixel.
   */
  ImageModel greenComp();

  /**
   * Provides the blue composition of the pixel.
   *
   * @return the blue composition of the pixel.
   */
  ImageModel blueComp();

  /**
   * Calculates the maximum value of the three components for each pixel.
   *
   * @return the maximum value of the three components for each pixel.
   */
  ImageModel value();

  /**
   * Calculates the average of the three components for each pixel.
   *
   * @return the average of the three components for each pixel.
   */
  ImageModel intensity();

  /**
   * Calculates the weighted sum with the formula (0.2126𝑟+0.7152𝑔+0.0722𝑏)
   *
   * @return the weighted sum with the above formula.
   */
  ImageModel luma();

  /**
   * Performs a vertical flip.
   *
   * @return a vertical flip of the image.
   */
  ImageModel vertical();

  /**
   * Performs a horizontal flip.
   *
   * @return a horizontal flip of the image.
   */
  ImageModel horizontal();

  /**
   * Takes in n and changes the brightness of the image depending on the value of n.
   *
   * @param n the value the the brightness is wished to be change to.
   * @return an image with changed brightness.
   */
  ImageModel brightness(int n);

  /**
   * Filters the image.
   * @param type the type of input file.
   * @return the image as it is filtered.
   */
  ImageModel filter(String type);

  /**
   * Changes the color of the image.
   * @param type the type of input file.
   * @return the image as it is changed.
   */
  ImageModel colorChange(String type);

  /**
   * Gets the color from the array.
   *
   * @return the color.
   */
  Color[][] getColors();


  /**
   * Gets the row from the array.
   *
   * @return the row.
   */
  int getRows();

  /**
   * Gets the columns from the array.
   *
   * @return the column.
   */
  int getColumns();

  /**
   * Gets the max value from the array.
   *
   * @return the max value.
   */
  int getMax();

}
