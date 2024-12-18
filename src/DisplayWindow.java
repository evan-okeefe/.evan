import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class DisplayWindow {

    private final Pixel[][] p;

    private final JFrame frame = new JFrame("Image Display");
    private final ImagePanel imagePanel;
    private final JButton plusButton = new JButton("+");
    private final JButton minusButton = new JButton("-");
    private final JButton leftButton = new JButton("<");
    private final JButton rightButton = new JButton(">");
    private final JButton upButton = new JButton("^");
    private final JButton downButton = new JButton("v");
    private final JButton resetButton = new JButton("⟲");
    private final JButton editButton = new JButton("✎");
    private final JButton exitButton = new JButton("␛");
    private final JLabel infoLeft = new JLabel("Label 1", SwingConstants.CENTER);
    private final JLabel infoRight = new JLabel("Label 2", SwingConstants.CENTER);
    private final String imageName;


    public DisplayWindow(String path) throws IOException {
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
        topPanel.setLayout(new GridLayout(1, 2, 5, 5)); // 1 row, 2 columns, 5px gap
        infoLeft.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        infoRight.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        topPanel.add(infoLeft);
        topPanel.add(infoRight);

        // Add the top panel to the NORTH position
        frame.add(topPanel, BorderLayout.NORTH);

        // Create the bottom panel with buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Centered buttons with spacing

        // Add action listeners to the buttons
        plusButton.addActionListener(e -> {
            imagePanel.zoomIn();
            imagePanel.repaint();
            updateInfo();
        });
        minusButton.addActionListener(e -> {
            imagePanel.zoomOut();
            imagePanel.repaint();
            updateInfo();
        });
        upButton.addActionListener(e -> {
            imagePanel.moveVertical(-1);
            imagePanel.repaint();
            updateInfo();
        });
        downButton.addActionListener(e -> {
            imagePanel.moveVertical(1);
            imagePanel.repaint();
            updateInfo();
        });
        leftButton.addActionListener(e -> {
            imagePanel.moveHorizontal(-1);
            imagePanel.repaint();
            updateInfo();
        });
        resetButton.addActionListener(e -> {
            imagePanel.reset();
            imagePanel.repaint();
            updateInfo();
        });
        editButton.addActionListener(e -> {
            try {
                GuiApp.editImage();
                CLApp.editImage();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        exitButton.addActionListener(e -> {
            int restart = JOptionPane.showOptionDialog(
                    null, // Parent component (can be null)
                    "Would you like to close or restart?", // Message to display
                    "Convert Another?", // Title of the dialog box
                    JOptionPane.OK_OPTION, // Option type
                    JOptionPane.QUESTION_MESSAGE, // Message type (icon)
                    null, // Icon (can be null)
                    new String[]{"Close", "Restart"}, // Array of options
                    "Restart" // Default option
            );
            if (restart == 1) {
                try {
                    GuiApp.closeAll();
                    GuiApp.main(null);
                    CLApp.closeAll();
                    CLApp.main(new String[]{path});
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                System.exit(0);
            }
        });

        bottomPanel.add(plusButton);
        bottomPanel.add(minusButton);
        bottomPanel.add(new JLabel("  "));
        bottomPanel.add(leftButton);
        bottomPanel.add(upButton);
        bottomPanel.add(downButton);
        bottomPanel.add(rightButton);
        bottomPanel.add(new JLabel("  "));
        bottomPanel.add(resetButton);
        bottomPanel.add(editButton);
        bottomPanel.add(new JLabel("  "));
        bottomPanel.add(new JLabel("  "));
        bottomPanel.add(exitButton);


        // Add the bottom panel to the SOUTH position
        frame.add(bottomPanel, BorderLayout.SOUTH);

        updateInfo();
    }

    private void updateInfo() {
        infoLeft.setText(imageName + " (" + p.length + "x" + p[0].length + ") [" + (int) (imagePanel.getZoomAmount() * 100) + "%]");
        infoRight.setText("Location: (" + imagePanel.getxLocMod() + ", " + imagePanel.getyLocMod() * -1 + ")");
    }

    public void activate() {
        frame.setVisible(true);
    }

    public void deactivate() {
        frame.setVisible(false);
    }
}
