import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class CLApp {

    static DisplayWindow mainDisplayWindow;
    static EditWindow editWindow;

    static String currPath;

    public static void main(String[] args) throws IOException {
        String path = args[0];
        currPath = path;

        mainDisplayWindow = new DisplayWindow(currPath);
        mainDisplayWindow.activate();
    }

    public static void editImage() throws IOException {
        editWindow = new EditWindow(currPath);
        mainDisplayWindow.deactivate();
        editWindow.activate();
    }

    public static void displayImage() throws IOException {
        mainDisplayWindow = new DisplayWindow(currPath);
        editWindow.deactivate();
        mainDisplayWindow.activate();
    }

    public static void closeAll() throws IOException {
        mainDisplayWindow.deactivate();
        editWindow.deactivate();
        main(null);
    }

}
