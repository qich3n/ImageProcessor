package view;

import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;

import controller.ImageController;
import model.ImageModel;
import model.ImageModelImpl;

/**
 * Creates a window that supports the Image Processing actions from the model and controller.
 */
public class ImageViewImpl extends JFrame implements ActionListener {
  private static final int IMAGE_ROWS = 500;
  private static final int IMAGE_COLUMNS = 700;
  private final ImageController controller;
  private ImageModel model;
  private final JPanel graphArea;
  private final JLabel image;
  private HistogramViewImpl histogram;
  private final JLabel graph;


  /**
   * Constructor for ImageViewImpl, implements the GUI features and displays the GUI.
   * @param controller an instance of ImageController that represents a command.
   */
  public ImageViewImpl(ImageController controller) {
    super();
    this.controller = controller;

    setTitle("Image Processor");
    setSize(1280, 720);

    // Represents the main window
    JPanel window = new JPanel();
    window.setLayout(new BorderLayout());
    JScrollPane scrollPane = new JScrollPane(window);
    add(scrollPane);

    // Represents the image area
    JPanel image = new JPanel();
    image.setLayout(new FlowLayout());
    window.add(image, BorderLayout.PAGE_START);

    // This portion initialize graph
    graph = new JLabel();

    // This portion represents the histogram area
    graphArea = new JPanel();
    graphArea.setBorder(BorderFactory.createTitledBorder("Histogram"));
    graphArea.add(graph);
    window.add(graphArea);

    // This portion represents the location of the operation buttons
    JPanel operations = new JPanel();
    operations.setLayout(new BoxLayout(operations, BoxLayout.Y_AXIS));
    image.add(operations);

    // This portion represents where the input output buttons are
    JPanel loadSave = new JPanel();
    loadSave.setLayout(new BoxLayout(loadSave, BoxLayout.Y_AXIS));
    loadSave.setBorder(new TitledBorder("I/O"));
    operations.add(loadSave);

    // This portion represents the Operations
    JPanel actions = new JPanel();
    actions.setLayout(new BoxLayout(actions, BoxLayout.Y_AXIS));
    actions.setBorder(new TitledBorder("Operations"));
    operations.add(actions);

    //  This portion initializes the image
    this.image = new JLabel();
    this.image.setSize(new Dimension(IMAGE_COLUMNS, IMAGE_ROWS));
    this.image.setText("Load Image");
    this.image.setFont(new Font("Verdana", Font.PLAIN, 30));
    this.image.setBorder(new TitledBorder("Image"));
    image.add(this.image);

    // Below are all the operations for the GUI
    JButton redButton = new JButton("Red");
    redButton.setActionCommand("Red");
    redButton.addActionListener(this);
    actions.add(redButton);

    JButton greenButton = new JButton("Green");
    greenButton.setActionCommand("Green");
    greenButton.addActionListener(this);
    actions.add(greenButton);

    JButton blueButton = new JButton("Blue");
    blueButton.setActionCommand("Blue");
    blueButton.addActionListener(this);
    actions.add(blueButton);

    JButton intensityButton = new JButton("Intensity");
    intensityButton.setActionCommand("Intensity");
    intensityButton.addActionListener(this);
    actions.add(intensityButton);

    JButton lumaButton = new JButton("Luma");
    lumaButton.setActionCommand("Luma");
    lumaButton.addActionListener(this);
    actions.add(lumaButton);

    JButton valueButton = new JButton("Value");
    valueButton.setActionCommand("Value");
    valueButton.addActionListener(this);
    actions.add(valueButton);

    JButton greyscaleButton = new JButton("Greyscale");
    greyscaleButton.setActionCommand("Greyscale");
    greyscaleButton.addActionListener(this);
    actions.add(greyscaleButton);

    JButton sepiaButton = new JButton("Sepia");
    sepiaButton.setActionCommand("Sepia");
    sepiaButton.addActionListener(this);
    actions.add(sepiaButton);

    JButton brightenButton = new JButton("Brighten");
    brightenButton.setActionCommand("Brighten");
    brightenButton.addActionListener(this);
    actions.add(brightenButton);

    JButton blurButton = new JButton("Blur");
    blurButton.setActionCommand("Blur");
    blurButton.addActionListener(this);
    actions.add(blurButton);

    JButton sharpenButton = new JButton("Sharpen");
    sharpenButton.setActionCommand("Sharpen");
    sharpenButton.addActionListener(this);
    actions.add(sharpenButton);

    JButton verticalButton = new JButton("Vertical");
    verticalButton.setActionCommand("Vertical");
    verticalButton.addActionListener(this);
    actions.add(verticalButton);

    JButton horizontalButton = new JButton("Horizontal");
    horizontalButton.setActionCommand("Horizontal");
    horizontalButton.addActionListener(this);
    actions.add(horizontalButton);

    JButton saveButton = new JButton("Save");
    saveButton.setActionCommand("Save");
    saveButton.addActionListener(this);
    loadSave.add(saveButton);

    JButton loadButton = new JButton("Load");
    loadButton.setActionCommand("Load");
    loadButton.addActionListener(this);
    loadSave.add(loadButton);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Red":
        if (model != null) {
          model = model.redComp();
          image.setIcon(ImageViewImpl.fitImage(new ImageIcon(createImage(model))));
          graphArea.remove(this.histogram);
          this.histogram = new HistogramViewImpl(this.model.getColors());
          graphArea.add(this.histogram);
        }
        break;
      case "Green":
        if (model != null) {
          model = model.greenComp();
          image.setIcon(ImageViewImpl.fitImage(new ImageIcon(createImage(model))));
          graphArea.remove(this.histogram);
          this.histogram = new HistogramViewImpl(this.model.getColors());
          graphArea.add(this.histogram);
        }
        break;
      case "Blue":
        if (model != null) {
          model = model.blueComp();
          image.setIcon(ImageViewImpl.fitImage(new ImageIcon(createImage(model))));
          graphArea.remove(this.histogram);
          this.histogram = new HistogramViewImpl(this.model.getColors());
          graphArea.add(this.histogram);
        }
        break;
      case "Luma":
        if (model != null) {
          model = model.luma();
          image.setIcon(ImageViewImpl.fitImage(new ImageIcon(createImage(model))));
          graphArea.remove(this.histogram);
          this.histogram = new HistogramViewImpl(this.model.getColors());
          graphArea.add(this.histogram);
        }
        break;
      case "Intensity":
        if (model != null) {
          model = model.intensity();
          image.setIcon(ImageViewImpl.fitImage(new ImageIcon(createImage(model))));
          graphArea.remove(this.histogram);
          this.histogram = new HistogramViewImpl(this.model.getColors());
          graphArea.add(this.histogram);
        }
        break;
      case "Value":
        if (model != null) {
          model = model.value();
          image.setIcon(ImageViewImpl.fitImage(new ImageIcon(createImage(model))));
          graph.setText("");
          graphArea.remove(this.histogram);
          this.histogram = new HistogramViewImpl(this.model.getColors());
          graphArea.add(this.histogram);
        }
        break;
      case "Greyscale":
        if (model != null) {
          model = model.colorChange("greyscale");
          image.setIcon(ImageViewImpl.fitImage(new ImageIcon(createImage(model))));
          graphArea.remove(this.histogram);
          this.histogram = new HistogramViewImpl(this.model.getColors());
          graphArea.add(this.histogram);
        }
        break;
      case "Sepia":
        if (model != null) {
          model = model.colorChange("sepia");
          image.setIcon(ImageViewImpl.fitImage(new ImageIcon(createImage(model))));
          graphArea.remove(this.histogram);
          this.histogram = new HistogramViewImpl(this.model.getColors());
          graphArea.add(this.histogram);
        }
        break;
      case "Blur":
        if (model != null) {
          model = model.filter("blur");
          image.setIcon(ImageViewImpl.fitImage(new ImageIcon(createImage(model))));
          graphArea.remove(this.histogram);
          this.histogram = new HistogramViewImpl(this.model.getColors());
          graphArea.add(this.histogram);
        }
        break;
      case "Sharpen":
        if (model != null) {
          model = model.filter("sharpen");
          image.setIcon(ImageViewImpl.fitImage(new ImageIcon(createImage(model))));
          graphArea.remove(this.histogram);
          this.histogram = new HistogramViewImpl(this.model.getColors());
          graphArea.add(this.histogram);
        }
        break;
      case "Brighten":
        boolean input = true;
        if (model != null) {
          while (input) {
            try {
              String userInput = JOptionPane.showInputDialog("Brighten by:");
              if (userInput == null || (userInput != null && ("".equals(userInput)))) {
                input = false;
              } else {
                model = model.brightness(Integer.parseInt(userInput));
              }
              input = false;
            } catch (NumberFormatException nfe) {
              input = true;
            }
          }
          image.setIcon(ImageViewImpl.fitImage(new ImageIcon(createImage(model))));
          graphArea.remove(this.histogram);
          this.histogram = new HistogramViewImpl(this.model.getColors());
          graphArea.add(this.histogram);
        }
        break;
      case "Vertical":
        if (model != null) {
          model = model.vertical();
          image.setIcon(ImageViewImpl.fitImage(new ImageIcon(createImage(model))));
          graphArea.remove(this.histogram);
          this.histogram = new HistogramViewImpl(this.model.getColors());
          graphArea.add(this.histogram);
        }
        break;
      case "Horizontal":
        if (model != null) {
          model = model.horizontal();
          image.setIcon(ImageViewImpl.fitImage(new ImageIcon(createImage(model))));
          graphArea.remove(this.histogram);
          this.histogram = new HistogramViewImpl(this.model.getColors());
          graphArea.add(this.histogram);
        }
        break;
      case "Load":
        final JFileChooser load = new JFileChooser(".");
        int loadVal = load.showOpenDialog(ImageViewImpl.this);
        if (loadVal == JFileChooser.APPROVE_OPTION) {
          File f = load.getSelectedFile();
          model = new ImageModelImpl(controller.loadImage(f));
          image.setIcon(ImageViewImpl.fitImage(new ImageIcon(createImage(model))));
          image.setText("");
          this.histogram = new HistogramViewImpl(model.getColors());
          graphArea.add(this.histogram);
        }
        break;
      case "Save":
        final JFileChooser save = new JFileChooser(".");
        int saveVal = save.showSaveDialog(ImageViewImpl.this);
        if (saveVal == JFileChooser.APPROVE_OPTION) {
          File file = save.getSelectedFile();
          controller.saveImage(file, model);
          this.histogram = new HistogramViewImpl(model.getColors());
          graphArea.add(this.histogram);
        }
        break;
      default:
        break;
    }
  }

  private static ImageIcon fitImage(ImageIcon image) {
    int newColumns = image.getIconWidth();
    int newRows = image.getIconHeight();
    if (image.getIconWidth() > IMAGE_COLUMNS) {
      newColumns = IMAGE_COLUMNS;
      newRows = (newColumns * image.getIconHeight()) / image.getIconWidth();
    }
    if (newRows > IMAGE_ROWS) {
      newRows = IMAGE_ROWS;
      newColumns = (image.getIconWidth() * newRows) / image.getIconHeight();
    }

    return new ImageIcon(image.getImage().getScaledInstance(newColumns, newRows,
            Image.SCALE_DEFAULT));
  }

  private BufferedImage createImage(ImageModel model) {
    BufferedImage image = new BufferedImage(model.getColumns(), model.getRows(),
            BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < model.getRows(); i++) {
      for (int j = 0; j < model.getColumns(); j++) {
        int rgb = model.getColors()[i][j].getRGB();
        image.setRGB(j, i, rgb);
      }
    }
    return image;
  }
}
