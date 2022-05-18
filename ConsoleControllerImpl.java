package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


import model.ImageModelImpl;
import model.ImageModel;


/**
 * Represents the ConsoleControllerImpl class and the premise of this class is to support different
 * commands such as load, save, and the different edit features.
 */
public class ConsoleControllerImpl extends AbstractControllerImpl {
  private Map<String, ImageModel> images;
  private InputStream in;

  /**
   * Represents an Instance of the ConsoleController that is waiting for commands to take in.
   * @param input represents the command to take in
   */
  public ConsoleControllerImpl(InputStream input) {
    this.images = new HashMap<>();
    this.in = input;
  }

  /**
   * Represents an Instance of the ConsoleController that does not take in commands.
   */
  public ConsoleControllerImpl() {
    this.images = new HashMap<>();
    this.in = System.in;
  }


  @Override
  public void imageProcessor() throws IOException {
    Scanner sc = new Scanner(this.in);
    String current = "";
    String updated = "";
    File newFile;
    String name = "";
    while (sc.hasNext()) {
      String op = sc.next();
      switch (op) {
        case "save":
          newFile = new File(sc.next());
          name = sc.next();
          saveImage(newFile, images.get(name));
          break;
        case "load":
          newFile = new File(sc.next());
          name = sc.next();
          if (images.containsKey(name)) {
            images.replace(name, new ImageModelImpl(loadImage(newFile)));
          } else {
            images.put(name, new ImageModelImpl(loadImage(newFile)));
          }
          break;
        case "vertical":
          current = sc.next();
          updated = sc.next();
          images.put(updated, images.get(current).vertical());
          break;
        case "horizontal":
          current = sc.next();
          updated = sc.next();
          images.put(updated, images.get(current).horizontal());
          break;
        case "value":
          current = sc.next();
          updated = sc.next();
          images.put(updated, images.get(current).value());
          break;
        case "intensity":
          current = sc.next();
          updated = sc.next();
          images.put(updated, images.get(current).intensity());
          break;
        case "luma":
          current = sc.next();
          updated = sc.next();
          images.put(updated, images.get(current).luma());
          break;
        case "red":
          current = sc.next();
          updated = sc.next();
          images.put(updated, images.get(current).redComp());
          break;
        case "green":
          current = sc.next();
          updated = sc.next();
          images.put(updated, images.get(current).greenComp());
          break;
        case "blue":
          current = sc.next();
          updated = sc.next();
          images.put(updated, images.get(current).blueComp());
          break;
        case "brighten":
          int inc = sc.nextInt();
          current = sc.next();
          updated = sc.next();
          images.put(updated, images.get(current).brightness(inc));
          break;
        case "greyscale":
          current = sc.next();
          updated = sc.next();
          images.put(updated, images.get(current).colorChange("greyscale"));
          break;
        case "sepia":
          current = sc.next();
          updated = sc.next();
          images.put(updated, images.get(current).colorChange("sepia"));
          break;
        case "blur":
          current = sc.next();
          updated = sc.next();
          images.put(updated, images.get(current).filter("blur"));
          break;
        case "sharpen":
          current = sc.next();
          updated = sc.next();
          images.put(updated, images.get(current).filter("sharpen"));
          break;
        case "q":
          return;
        default:
          throw new IllegalStateException("Unexpected value: " + op);
      }
    }
  }
}




