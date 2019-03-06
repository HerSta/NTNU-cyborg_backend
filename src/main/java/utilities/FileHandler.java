package utilities;

import java.awt.*;
import java.io.File;
import java.util.Scanner;


public class FileHandler {

    public void ReadCSV() {
        FileDialog dialog = new FileDialog((Frame) null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);

        String file = dialog.getFile();
        String directory = dialog.getDirectory();

        System.out.println(file + " chosen.");
        System.out.println(directory + " directory");

        try {
            Scanner scanner = new Scanner(new File(directory + file));
            int counter = 0;
            String text = "";
            while (scanner.hasNextLine()) {
                //System.out.println(scanner.nextLine());

                if (counter < 10 && counter >= 0) {
                    text = scanner.nextLine();
                    System.out.println(text);
                }
                counter++;
            }
            System.out.println("ENDED " + counter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
