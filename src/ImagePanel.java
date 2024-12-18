import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {
    private BufferedImage image;

    Color[][] originalColors;

    private double zoomAmount = 1.0;

    private int xLocMod = 0;
    private int yLocMod = 0;

    public int getxLocMod() {
        return xLocMod;
    }

    public int getyLocMod() {
        return yLocMod;
    }

    public double getZoomAmount() {
        return zoomAmount;
    }

    public ImagePanel(BufferedImage image) {
        this.image = image;
        originalColors = new Color[image.getWidth()][image.getHeight()];
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                originalColors[x][y] = new Color(image.getRGB(x, y));
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            // Get the panel dimensions
            int panelWidth = getWidth();
            int panelHeight = getHeight();

            // Calculate the scaling factor
            double scaleX = (double) panelWidth / image.getWidth() * zoomAmount;
            double scaleY = (double) panelHeight / image.getHeight() * zoomAmount;
            double scale = Math.min(scaleX, scaleY); // Preserve aspect ratio

            // Calculate the new dimensions of the image
            int newWidth = (int) (image.getWidth() * scale);
            int newHeight = (int) (image.getHeight() * scale);

            // Center the image
            int x = (panelWidth - newWidth) / 2 + xLocMod;
            int y = (panelHeight - newHeight) / 2 + yLocMod;

            // Draw the scaled image
            g.drawImage(image, x, y, newWidth, newHeight, this);
        }
    }

    public void zoomIn() {
        zoomAmount += 0.05;
        if (zoomAmount <= 0) {
            zoomAmount = 0;
        }
    }
    public void zoomOut() {
        zoomAmount -= 0.05;
        if (zoomAmount <= 0) {
            zoomAmount = 0;
        }
    }

    public void moveHorizontal(int dir) {
        xLocMod += dir * 10;
    }
    public void moveVertical(int dir) {
        yLocMod += dir * 10;
    }

    public void reset() {
        zoomAmount = 1.0;
        xLocMod = 0;
        yLocMod = 0;
    }

    public void filterImage(int filter) {
        //Original
        if (filter == 0){
            System.out.println("Original");
           for (int x = 0; x < originalColors.length; x++) {
                for (int y = 0; y < originalColors[0].length; y++) {
                    image.setRGB(x, y, originalColors[x][y].getRGB());
                }
            }
        }
        //Grayscale
        else if (filter == 1){
            System.out.println("Grayscale");
            for (int x = 0; x < originalColors.length; x++) {
                for (int y = 0; y < originalColors[0].length; y++) {
                    int rgb = originalColors[x][y].getRGB();
                    Color color = new Color(rgb);
                    int r = color.getRed();
                    int g = color.getGreen();
                    int b = color.getBlue();
                    int avg = (r + g + b) / 3;
                    int newRGB = new Color(avg, avg, avg).getRGB();
                    image.setRGB(x, y, newRGB);
                }
            }
        }
        //Sepia
        else if (filter == 2){
            System.out.println("Sepia");
            for (int x = 0; x < originalColors.length; x++) {
                for (int y = 0; y < originalColors[0].length; y++) {
                    Color color = new Color(originalColors[x][y].getRGB());
                    int r = color.getRed();
                    int g = color.getGreen();
                    int b = color.getBlue();
                    int newRed = (int)(0.393*r + 0.769*g + 0.189*b);
                    int newGreen = (int)(0.349*r + 0.686*g + 0.168*b);
                    int newBlue = (int)(0.272*r + 0.534*g + 0.131*b);

                    r = Math.min(newRed, 255);

                    g = Math.min(newGreen, 255);

                    b = Math.min(newBlue, 255);

                    image.setRGB(x, y, new Color(r, g, b).getRGB());
                }
            }
        }
        //Invert
        else if (filter == 3){
            System.out.println("Invert");
            for (int x = 0; x < originalColors.length; x++) {
                for (int y = 0; y < originalColors[0].length; y++) {
                    Color color = new Color(originalColors[x][y].getRGB());
                    int r = color.getRed();
                    int g = color.getGreen();
                    int b = color.getBlue();
                    int newRed = 255 - r;
                    int newGreen = 255 - g;
                    int newBlue = 255 - b;
                    image.setRGB(x, y, new Color(newRed, newGreen, newBlue).getRGB());
                }
            }
        }
        //Red
        else if (filter == 4){
            System.out.println("Red");
            for (int x = 0; x < originalColors.length; x++) {
                for (int y = 0; y < originalColors[0].length; y++) {
                    Color color = new Color(originalColors[x][y].getRGB());
                    int r = color.getRed();
                    image.setRGB(x, y, new Color(r, 0, 0).getRGB());
                }
            }
        }
        //Green
        else if (filter == 5){
            System.out.println("Green");
            for (int x = 0; x < originalColors.length; x++) {
                for (int y = 0; y < originalColors[0].length; y++) {
                    Color color = new Color(originalColors[x][y].getRGB());
                    int g = color.getGreen();
                    image.setRGB(x, y, new Color(0, g, 0).getRGB());
                }
            }
        }
        //Blue
        else if (filter == 6){
            System.out.println("Blue");
            for (int x = 0; x < originalColors.length; x++) {
                for (int y = 0; y < originalColors[0].length; y++) {
                    Color color = new Color(originalColors[x][y].getRGB());
                    int b = color.getBlue();
                    image.setRGB(x, y, new Color(0, 0, b).getRGB());
                }
            }
        }
        //Cyan
        else if (filter == 7){
            System.out.println("Cyan");
            for (int x = 0; x < originalColors.length; x++) {
                for (int y = 0; y < originalColors[0].length; y++) {
                    Color color = new Color(originalColors[x][y].getRGB());
                    int g = color.getGreen();
                    int b = color.getBlue();
                    image.setRGB(x, y, new Color(0, g, b).getRGB());
                }
            }
        }
        //Magenta
        else if (filter == 8){
            System.out.println("Magenta");
            for (int x = 0; x < originalColors.length; x++) {
                for (int y = 0; y < originalColors[0].length; y++) {
                    Color color = new Color(originalColors[x][y].getRGB());
                    int r = color.getRed();
                    int b = color.getBlue();
                    image.setRGB(x, y, new Color(r, 0, b).getRGB());
                }
            }
        }
        //Yellow
        else if (filter == 9){
            System.out.println("Yellow");
            for (int x = 0; x < originalColors.length; x++) {
                for (int y = 0; y < originalColors[0].length; y++) {
                    Color color = new Color(originalColors[x][y].getRGB());
                    int r = color.getRed();
                    int g = color.getGreen();
                    image.setRGB(x, y, new Color(r, g, 0).getRGB());
                }
            }
        }

        repaint();

    }

    public BufferedImage getImage() {
        return image;
    }

    public void rotateImageRight() {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage rotatedImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
        Color[][] rotatedColors = new Color[height][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                rotatedImage.setRGB(height - y - 1, x, image.getRGB(x, y));
                rotatedColors[height - y - 1][x] = originalColors[x][y];
            }
        }

        image = rotatedImage;
        originalColors = rotatedColors;
        repaint();
    }

    public void rotateImageLeft() {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage rotatedImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
        Color[][] rotatedColors = new Color[height][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                rotatedImage.setRGB(y, width - x - 1, image.getRGB(x, y));
                rotatedColors[y][width - x - 1] = originalColors[x][y];
            }
        }

        image = rotatedImage;
        originalColors = rotatedColors;
        repaint();
    }
}
