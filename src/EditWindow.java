import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class EditWindow {

    private final Pixel[][] p;

    private final JFrame frame = new JFrame("Image Display");
    private final ImagePanel imagePanel;
    private final JButton leftButton = new JButton("<");
    private final JButton rightButton = new JButton(">");
    private final JButton rotateLeftButton = new JButton("↶");
    private final JButton rotateRightButton = new JButton("↷");
    private final JButton resetButton = new JButton("⟲");
    private final JButton writeButton = new JButton("\uD83D\uDCC1");
    private final JLabel infoLeft = new JLabel("Label 1", SwingConstants.CENTER);
    private final JLabel infoCenter = new JLabel("Label 2", SwingConstants.CENTER);
    private final JLabel infoRight = new JLabel("Label 3", SwingConstants.CENTER);
    private final String imageName;

    private String[] filters = {"Original", "Grayscale", "Sepia", "Invert", "Red", "Green", "Blue", "Cyan", "Magenta", "Yellow"};
    private int filter = 0;

    public EditWindow(String path) throws IOException {

        //ImageClass.writeImage("images/test.png", "ryan");
        imageName = path;
        p = ImageClass.readImage(path);

        // Read the image from a file
        BufferedImage image = ImageClass.convertToImage(p);

        // Rotate the image 90 degrees and flip it horizontally
        image = ImageClass.rotateImage(image);
        image = ImageClass.flipImage(image);

        imagePanel = new ImagePanel(image);

        // Create a JFrame to display the image
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout()); // Use BorderLayout

        // Add the image panel to the center
        frame.add(imagePanel, BorderLayout.CENTER);

        // Create the top panel with two text fields
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 3, 5, 5)); // 1 row, 2 columns, 5px gap
        infoLeft.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        infoCenter.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        infoRight.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        topPanel.add(infoLeft);
        topPanel.add(infoCenter);
        topPanel.add(infoRight);

        // Add the top panel to the NORTH position
        frame.add(topPanel, BorderLayout.NORTH);

        // Create the bottom panel with buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Centered buttons with spacing

        // Add action listeners to the buttons
        leftButton.addActionListener(e -> {
            if (filter == 0) {
                filter = filters.length - 1;
            } else {
                filter -= 1;
            }
            imagePanel.filterImage(filter);
            updateInfo();
        });
        rightButton.addActionListener(e -> {
           if (filter == filters.length - 1) {
               filter = 0;
           } else {
               filter += 1;
           }
           imagePanel.filterImage(filter);
           updateInfo();
        });
        resetButton.addActionListener(e -> {
            imagePanel.reset();
            imagePanel.filterImage(0);
            imagePanel.repaint();
            filter = 0;
            updateInfo();
        });
        writeButton.addActionListener(e -> {
            int choice = JOptionPane.showOptionDialog(
                    null, // Parent component (can be null)
                    "Would you like to save the image as a .evan file?\nYou will not be able to reverse any filter applied to the image.", // Message to display
                    "Save Image?", // Title of the dialog box
                    JOptionPane.OK_OPTION, // Option type
                    JOptionPane.QUESTION_MESSAGE, // Message type (icon)
                    null, // Icon (can be null)
                    new String[]{"Yes", "No"}, // Array of options
                    "Yes" // Default option
            );
            if (choice == 1) {
                return;
            }
            try {
                ImageClass.writeImage(imagePanel.getImage(), path.replace(".evan", ""));
                GuiApp.displayImage();
                CLApp.displayImage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });
        rotateLeftButton.addActionListener(e -> {
            imagePanel.rotateImageLeft();
            imagePanel.repaint();
        });
        rotateRightButton.addActionListener(e -> {
            imagePanel.rotateImageRight();
            imagePanel.repaint();
        });

        bottomPanel.add(leftButton);
        bottomPanel.add(rightButton);
        bottomPanel.add(new JLabel("  "));
        bottomPanel.add(rotateLeftButton);
        bottomPanel.add(rotateRightButton);
        bottomPanel.add(new JLabel("  "));
        bottomPanel.add(resetButton);
        bottomPanel.add(writeButton);


        // Add the bottom panel to the SOUTH position
        frame.add(bottomPanel, BorderLayout.SOUTH);

        updateInfo();
    }

    private void updateInfo() {
        infoLeft.setText(imageName + " (" + p.length + "x" + p[0].length + ") [" + (int) (imagePanel.getZoomAmount() * 100) + "%]");
        infoCenter.setText("Current Filter: " + filters[filter]);
        infoRight.setText("Location: (" + imagePanel.getxLocMod() + ", " + imagePanel.getyLocMod() * -1 + ")");
    }

    public void activate() {
        frame.setVisible(true);
    }

    public void deactivate() {
        frame.setVisible(false);
    }
}
