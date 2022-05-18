package controller;

import javax.swing.JFrame;

import view.ImageViewImpl;

/**
 * Represents the GUIControllerImpl Class and the purpose of this class is to execute
 * the program, and it would display the view window of the program. In the view window,
 *  the user has the ability to save, load, and perform edits on an image.
 */
public class GUIControllerImpl extends AbstractControllerImpl {

  @Override
  public void imageProcessor() {
    JFrame frame = new ImageViewImpl(this);
    frame.setDefaultLookAndFeelDecorated(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}
