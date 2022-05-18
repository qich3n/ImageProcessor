package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;


/**
 * The ImageModelImpl class implements all the methods from ImageModel and performs the
 * processes that allow the image processing application to work.
 */
public class ImageModelImpl implements ImageModel {
  Color[][] colors;
  int rows;
  int cols;
  int max;


  /**
   * Constructs an instance of an ImageModel from a Color Array.
   * @param colors Represents the color Array that it takes in
   * @throws IllegalArgumentException if the array is null
   */
  public ImageModelImpl(Color[][] colors) throws IllegalArgumentException {
    if (colors == null) {
      throw new IllegalArgumentException("Array is null");
    }
    this.colors = colors;
    this.cols = colors[0].length;
    this.rows = colors.length;
    this.max = 255;
  }

  /**
   * Constructs an instance of an ImageModel from a BufferedImage.
   *
   * @param image image read supplemented from image I/O
   */
  public ImageModelImpl(BufferedImage image) {
    this.rows = image.getHeight();
    this.cols = image.getWidth();
    this.max = 255;

    this.colors = new Color[rows][cols];

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        int pixel = image.getRGB(j, i);
        this.colors[i][j] = new Color((pixel & 0xff0000) >> 16, (pixel & 0xff00) >> 8,
                pixel & 0xff);
      }
    }
  }

  @Override
  public ImageModel redComp() {
    Color[][] temp = new Color[rows][cols];
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        temp[i][j] = new Color(this.colors[i][j].getRed(), this.colors[i][j].getRed(),
                this.colors[i][j].getRed());
      }
    }
    return new ImageModelImpl(temp);
  }

  @Override
  public ImageModel greenComp() {
    Color[][] temp = new Color[rows][cols];
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        temp[i][j] = new Color(this.colors[i][j].getGreen(), this.colors[i][j].getGreen(),
                this.colors[i][j].getGreen());
      }
    }
    return new ImageModelImpl(temp);
  }

  @Override
  public ImageModel blueComp() {
    Color[][] temp = new Color[rows][cols];
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        temp[i][j] = new Color(this.colors[i][j].getBlue(), this.colors[i][j].getBlue(),
                this.colors[i][j].getBlue());
      }
    }
    return new ImageModelImpl(temp);
  }

  @Override
  public ImageModel value() {
    Color[][] temp = new Color[rows][cols];
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        int value = Math.max(Math.max(this.colors[i][j].getRed(), this.colors[i][j].getBlue()),
                this.colors[i][j].getGreen());
        temp[i][j] = new Color(value, value, value);
      }
    }
    return new ImageModelImpl(temp);
  }

  @Override
  public ImageModel intensity() {
    Color[][] temp = new Color[rows][cols];
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        int intensity = (this.colors[i][j].getRed() + this.colors[i][j].getGreen()
                + this.colors[i][j].getBlue()) / 3;
        temp[i][j] = new Color(intensity, intensity, intensity);
      }
    }
    return new ImageModelImpl(temp);
  }

  @Override
  public ImageModel luma() {
    Color[][] temp = new Color[rows][cols];
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        int luma = (int) clamp((0.2126 * this.colors[i][j].getRed())
                + (0.7152 * this.colors[i][j].getGreen())
                + (0.0722 * this.colors[i][j].getBlue()));
        temp[i][j] = new Color(luma, luma, luma);
      }
    }
    return new ImageModelImpl(temp);
  }

  @Override
  public ImageModel vertical() {
    Color[][] temp = new Color[rows][cols];
    for (int i = this.rows; i > 0; i--) {
      for (int j = 0; j < this.cols; j++) {
        temp[rows - i][j] = this.colors[i - 1][j];
      }
    }
    return new ImageModelImpl(temp);
  }

  @Override
  public ImageModel horizontal() {
    Color[][] temp = new Color[rows][cols];
    for (int i = 0; i < this.rows; i++) {
      for (int j = this.cols; j > 0; j--) {
        temp[i][cols - j] = this.colors[i][j - 1];
      }
    }
    return new ImageModelImpl(temp);
  }

  @Override
  public ImageModel brightness(int n) {
    Color[][] temp = new Color[rows][cols];

    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        int newRed = 0;
        int newGreen = 0;
        int newBlue = 0;

        newRed = (int) clamp(this.colors[i][j].getRed() + n);
        newGreen = (int) clamp(this.colors[i][j].getGreen() + n);
        newBlue = (int) clamp(this.colors[i][j].getBlue() + n);

        temp[i][j] = new Color(newRed, newGreen, newBlue);
      }
    }

    return new ImageModelImpl(temp);
  }

  @Override
  public ImageModel filter(String type) {
    if (type.equals("blur")) {
      return this.blur();
    } else if (type.equals("sharpen")) {
      return this.sharpen();
    }
    return this;
  }

  @Override
  public ImageModel colorChange(String type) {
    if (type.equals("greyscale")) {
      return this.luma();
    } else if (type.equals("sepia")) {
      return this.sepia();
    }
    return this;
  }

  /**
   * This performs a sepia color change on an image.
   *
   * @return a new changed image.
   */
  private ImageModel sepia() {
    Color[][] temp = new Color[rows][cols];
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        int sepiaR = (int) clamp((0.393 * this.colors[i][j].getRed())
                + (0.769 * this.colors[i][j].getGreen())
                + (0.189 * this.colors[i][j].getBlue()));

        int sepiaG = (int) clamp((0.349 * this.colors[i][j].getRed())
                + (0.686 * this.colors[i][j].getGreen())
                + (0.168 * this.colors[i][j].getBlue()));

        int sepiaB = (int) clamp((0.272 * this.colors[i][j].getRed())
                + (0.534 * this.colors[i][j].getGreen())
                + (0.131 * this.colors[i][j].getBlue()));

        temp[i][j] = new Color(sepiaR, sepiaG, sepiaB);
      }
    }
    return new ImageModelImpl(temp);
  }

  @Override
  public Color[][] getColors() {
    return this.colors;
  }

  @Override
  public int getRows() {
    return this.rows;
  }

  @Override
  public int getColumns() {
    return this.cols;
  }

  @Override
  public int getMax() {
    return this.max;
  }

  private double clamp(double op) {
    if (op >= 255) {
      return 255;
    } else if (op <= 0) {
      return 0;
    } else {
      return op;
    }
  }

  private ImageModel blur() {

    BufferedImage image = this.createImage();
    BufferedImage newImage = image;

    float[] matrix = new float[]{
        0.0675f, 0.1250f, 0.0675f,
        0.1250f, 0.2500f, 0.1250f,
        0.0675f, 0.1250f, 0.0675f};

    BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, matrix));

    return new ImageModelImpl(op.filter(image, null));
  }

  private ImageModel sharpen() {

    BufferedImage image = this.createImage();
    BufferedImage newImage = image;

    float[] matrix = new float[]{
        -0.0675f, -0.0675f, -0.0675f, -0.0675f, -0.0675f,
        -0.0675f, 0.2500f, 0.2500f, 0.2500f, -0.0675f,
        -0.0675f, 0.2500f, 1.0000f, 0.2500f, -0.0675f,
        -0.0675f, 0.2500f, 0.2500f, 0.2500f, -0.0675f,
        -0.0675f, -0.0675f, -0.0675f, -0.0675f, -0.0675f};

    BufferedImageOp op = new ConvolveOp(new Kernel(5, 5, matrix));

    return new ImageModelImpl(op.filter(image, null));
  }

  private BufferedImage createImage() {

    BufferedImage image = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        int val = (this.colors[i][j].getRed() << 16) | (this.colors[i][j].getGreen() << 8)
                | (this.colors[i][j].getBlue());
        image.setRGB(j, i, val);
      }
    }

    return image;
  }
}
