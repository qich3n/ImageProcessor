package controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.ImageModel;

/**
 * The AbstractControllerImpl class implements the ImageController interface. Here, it abstracts
 * the loadImage and SaveImage methods.
 */
abstract class AbstractControllerImpl implements ImageController {

  @Override
  public Color[][] loadImage(File file) throws IllegalArgumentException {
    Color[][] colors = new Color[0][];
    int columns = 0;
    int rows = 0;
    int max = 0;
    if (!file.exists()) {
      throw new IllegalArgumentException("File doesn't exist");
    }
    String name = file.getName();
    if (name.endsWith("ppm")) {

      try {
        Scanner sc = new Scanner(new FileInputStream(file));
        StringBuilder builder = new StringBuilder();
        while (sc.hasNextLine()) {
          String s = sc.nextLine();
          if (s.charAt(0) != '#') {
            builder.append(s + System.lineSeparator());
          }
        }

        sc = new Scanner(builder.toString());

        String token;

        token = sc.next();
        if (!token.equals("P3")) {
          System.out.println("Invalid PPM file: plain RAW file should begin with P3");
        }
        columns = sc.nextInt();
        rows = sc.nextInt();
        max = sc.nextInt();

        colors = new Color[rows][columns];

        for (int i = 0; i < rows; i++) {
          for (int j = 0; j < columns; j++) {
            int r = sc.nextInt();
            int g = sc.nextInt();
            int b = sc.nextInt();
            colors[i][j] = new Color(r, g, b);
          }
        }

      } catch (FileNotFoundException e) {
        System.out.println("File " + file + " not found!");
      }
    }
    else if (name.endsWith("png")
            || name.endsWith("jpg")
            || name.endsWith("jpeg")
            || name.endsWith("bmp")) {
      try {
        BufferedImage image = ImageIO.read(file);
        rows = image.getHeight();
        columns = image.getWidth();
        colors = new Color[rows][columns];

        for (int i = 0; i < rows; i++) {
          for (int j = 0; j < columns; j++) {
            int rgb = image.getRGB(j, i);
            colors[i][j] = new Color(rgb);
          }
        }
      } catch (IOException e) {
        System.out.println("IOException: " + e);
      }

    } else {
      throw new IllegalArgumentException("Incorrect File format");
    }
    return colors;
  }

  @Override
  public void saveImage(File file, ImageModel mod) {
    Color[][] colors = mod.getColors();
    String name = file.getName();
    if (name.endsWith("ppm")) {
      try {
        FileWriter writer = new FileWriter(file);
        writer.append(this.renderPPM(name, mod));
        writer.close();
      } catch (IOException e) {
        throw new IllegalArgumentException("IOException: " + e);
      }
    } else if (name.endsWith("png")
            || name.endsWith("jpg")
            || name.endsWith("bmp")
            || name.endsWith("jpeg")) {
      try {
        BufferedImage image =
                new BufferedImage(mod.getColumns(), mod.getRows(), BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < mod.getRows(); i++) {
          for (int j = 0; j < mod.getColumns(); j++) {
            int rgb = colors[i][j].getRGB();
            image.setRGB(j, i, rgb);
          }
        }

        String filePath = file.getPath();
        File newFile = new File(filePath);
        String endsWith = filePath.substring(filePath.length() - 3);

        ImageIO.write(image, endsWith, newFile);
      } catch (IOException e) {
        System.out.println("IOException: " + e);
      }
    } else {
      throw new IllegalArgumentException("Incorrect File format");
    }
  }

  protected String renderPPM(String fileName, ImageModel mod) {
    StringBuilder str = new StringBuilder();
    str.append("P3\n# " + fileName + "\n" + mod.getColumns() + " " + mod.getRows() + "\n" +
            mod.getMax() + "\n");
    for (int i = 0; i < mod.getRows(); i++) {
      for (int j = 0; j < mod.getColumns(); j++) {
        str.append(mod.getColors()[i][j].getRed() + "\n");
        str.append(mod.getColors()[i][j].getGreen() + "\n");
        str.append(mod.getColors()[i][j].getBlue() + "\n");
      }
    }
    return str.toString();
  }
}
