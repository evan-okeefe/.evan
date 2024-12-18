import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ImageClass {

    public static void writeImage(String path, String name) throws IOException {
        File file= new File(path);
        BufferedImage img = ImageIO.read(file);
        int count = 0;
        StringBuilder rgbValues = new StringBuilder();
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int pixel = img.getRGB(x,y);
                String rgb = Integer.toHexString(pixel * -1);

                rgbValues.append(rgb);
                rgbValues.append(" ");

                count++;
            }
            rgbValues.append("\n");
        }

        try (
                ByteArrayInputStream bais = new ByteArrayInputStream(rgbValues.toString().getBytes());
                FileOutputStream fos = new FileOutputStream(name + ".evan");
                GZIPOutputStream gzip = new GZIPOutputStream(fos);
        ) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bais.read(buffer)) != -1) {
                gzip.write(buffer, 0, len);
            }
        }
    }

    public static void writeImage(BufferedImage img, String name) throws IOException {
        int count = 0;
        StringBuilder rgbValues = new StringBuilder();
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int pixel = img.getRGB(x,y);
                String rgb = Integer.toHexString(pixel * -1);

                rgbValues.append(rgb);
                rgbValues.append(" ");

                count++;
            }
            rgbValues.append("\n");
        }

        try (
                ByteArrayInputStream bais = new ByteArrayInputStream(rgbValues.toString().getBytes());
                FileOutputStream fos = new FileOutputStream(name + ".evan");
                GZIPOutputStream gzip = new GZIPOutputStream(fos);
        ) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bais.read(buffer)) != -1) {
                gzip.write(buffer, 0, len);
            }
        }
    }

    public static Pixel[][] readImage(String path) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        try (
                FileInputStream fis = new FileInputStream(path);
                GZIPInputStream gis = new GZIPInputStream(fis);
                InputStreamReader isr = new InputStreamReader(gis);
                BufferedReader reader = new BufferedReader(isr)
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        Pixel[][] pixelArray = new Pixel[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            String[] rgbValues = lines.get(i).split(" ");
            pixelArray[i] = new Pixel[rgbValues.length];
            for (int j = 0; j < rgbValues.length; j++) {
                int rgb = Integer.parseInt(rgbValues[j], 16) * -1;
                Color color = new Color(rgb);
                pixelArray[i][j] = new Pixel(color);
            }
        }
        return pixelArray;
    }

    public static BufferedImage convertToImage(Pixel[][] pixels) {
        int width = pixels.length;
        int height = pixels[0].length;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, pixels[x][y].getColor().getRGB());
            }
        }

        return image;
    }

    public static BufferedImage rotateImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage rotatedImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                rotatedImage.setRGB(height - y - 1, x, image.getRGB(x, y));
            }
        }

        return rotatedImage;
    }

    public static BufferedImage flipImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage flippedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                flippedImage.setRGB(width - x - 1, y, image.getRGB(x, y));
            }
        }

        return flippedImage;
    }
}
