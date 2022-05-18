package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.JPanel;

import model.ImageModel;
import model.ImageModelImpl;

/**
 * The HistogramViewImpl class contains a JPanel which displays a histogram that represents
 * the occurrence of pixel values in the given image.
 */
public class HistogramViewImpl extends JPanel {

  private ArrayList<Integer> redComp;
  private ArrayList<Integer> greenComp;
  private ArrayList<Integer> blueComp;
  private ArrayList<Integer> allComp;
  private double rows;
  private double columns;
  private int max;


  /**
   * Constructor for the HistogramViewImpl JPanel,
   * this constructor initializes all 4 arrays of the pixel data to be displayed.
   * @param array represents the color array that the constructor takes in.
   */
  public HistogramViewImpl(Color[][] array) {
    this.redComp = new ArrayList<>(Arrays.asList(new Integer[256]));
    this.greenComp = new ArrayList<>(Arrays.asList(new Integer[256]));
    this.blueComp = new ArrayList<>(Arrays.asList(new Integer[256]));
    this.allComp = new ArrayList<>();
    Collections.fill(redComp, 0);
    Collections.fill(greenComp, 0);
    Collections.fill(blueComp, 0);
    int temp;
    ImageModel model = new ImageModelImpl(array);
    for (int r = 0; r < model.getRows(); r++) {
      for (int c = 0; c < model.getColumns(); c++) {
        Color pixel = model.getColors()[r][c];
        temp = redComp.get(pixel.getRed());
        temp++;
        redComp.set(pixel.getRed(), temp);
        temp = greenComp.get(pixel.getGreen());
        temp++;
        greenComp.set(pixel.getGreen(), temp);
        temp = blueComp.get(pixel.getBlue());
        temp++;
        blueComp.set(pixel.getBlue(), temp);
      }
    }

    this.max = 0;
    for (int i = 0; i < 256; i++) {
      this.allComp.add(redComp.get(i) + greenComp.get(i) + blueComp.get(i));
      this.max = Math.max(this.max, allComp.get(i));
    }

    this.rows = 250.00 / this.max;
    this.columns = 750.00 / 256;

    setPreferredSize(new Dimension(750, 250));
  }

  @Override
  protected void paintComponent(Graphics histogram) {

    histogram.drawLine(0, 250, 0, 0);
    histogram.drawLine(0, 250, 300, 250);

    for (int i = 0; i < 255; i++) {
      //red
      histogram.setColor(Color.RED);
      int redA = (int)((this.max - this.redComp.get(i)) * rows);
      int redB = (int)((this.max - this.redComp.get(i + 1)) * rows);
      histogram.drawLine((int)(i * columns), redA, (int)((i + 1) * columns), redB);
      //green
      histogram.setColor(Color.GREEN);
      int greenA = (int)((this.max - this.greenComp.get(i)) * rows);
      int greenB = (int)((this.max - this.greenComp.get(i + 1)) * rows);
      histogram.drawLine((int)(i * columns), greenA, (int)((i + 1) * columns), greenB);
      //blue
      histogram.setColor(Color.BLUE);
      int blueA = (int)((this.max - this.blueComp.get(i)) * rows);
      int blueB = (int)((this.max - this.blueComp.get(i + 1)) * rows);
      histogram.drawLine((int)(i * columns), blueA, (int)((i + 1) * columns), blueB);
      //black
      histogram.setColor(Color.BLACK);
      int allA = (int)((this.max - this.allComp.get(i)) * rows);
      int allB = (int)((this.max - this.allComp.get(i + 1)) * rows);
      histogram.drawLine((int)(i * columns), allA, (int)((i + 1) * columns), allB);
    }
  }
}
