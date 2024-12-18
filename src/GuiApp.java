import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GuiApp {

    static DisplayWindow mainDisplayWindow;
    static EditWindow editWindow;

    static String currPath;

    public static void main(String[] args) throws IOException {
        int convertOrSave = JOptionPane.showOptionDialog(
                null, // Parent component (can be null)
                "Would you like to convert an image or view a \".evan\" file?", // Message to display
                "Convert or View?", // Title of the dialog box
                JOptionPane.OK_OPTION, // Option type
                JOptionPane.QUESTION_MESSAGE, // Message type (icon)
                null, // Icon (can be null)
                new String[]{"Convert", "View", "Exit"}, // Array of options
                "Exit" // Default option
        );
        if (convertOrSave == 0) {
            FileDialog toConvert = new FileDialog((Frame)null, "Select Image to Convert");
            toConvert.setVisible(true);

            String path = toConvert.getDirectory() + toConvert.getFile();

            FileDialog saveAs = new FileDialog((Frame)null, "Save As", FileDialog.SAVE);
            saveAs.setVisible(true);

            String savePath = saveAs.getDirectory() + saveAs.getFile();



            String fileExtension = toConvert.getFile().split("\\.")[toConvert.getFile().split("\\.").length - 1];

            String[] validExtensions = {"jpg", "jpeg", "png"};

            System.out.println(fileExtension);

            if (!java.util.Arrays.asList(validExtensions).contains(fileExtension)) {
                JOptionPane.showMessageDialog(null, "Invalid file type. Please select a valid image file.", "Error", JOptionPane.ERROR_MESSAGE);
                int restart = JOptionPane.showOptionDialog(
                        null, // Parent component (can be null)
                        "Would you like to close or restart?", // Message to display
                        "Continue?", // Title of the dialog box
                        JOptionPane.OK_OPTION, // Option type
                        JOptionPane.QUESTION_MESSAGE, // Message type (icon)
                        null, // Icon (can be null)
                        new String[]{"Close", "Restart"}, // Array of options
                        "Restart" // Default option
                );
                if (restart == 1) {
                    GuiApp.main(null);
                }
                else {
                    System.exit(0);
                }
            }
            else {
                ImageClass.writeImage(path, savePath);
            }



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
                GuiApp.main(null);
            }
            else {
                System.exit(0);
            }
        }
        else if (convertOrSave == 1) {
            FileDialog toView = new FileDialog((Frame)null, "Select File to View");
            toView.setVisible(true);

            currPath = toView.getDirectory() + toView.getFile();

            mainDisplayWindow = new DisplayWindow(currPath);
            mainDisplayWindow.activate();
        }
        else {
            System.exit(0);
        }
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
