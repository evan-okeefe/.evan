import java.awt.*;

public class Pixel {
    private Color color;
    private int red;
    private int green;
    private int blue;

    public Pixel(Color color) {
        this.color = color;
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
    }

    public Color getColor() {
        return color;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public void setColor(Color color) {
        this.color = color;
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
    }

    public void setRed(int red) {
        this.red = red;
        this.color = new Color(red, green, blue);
    }

    public void setGreen(int green) {
        this.green = green;
        this.color = new Color(red, green, blue);
    }

    public void setBlue(int blue) {
        this.blue = blue;
        this.color = new Color(red, green, blue);
    }
}
