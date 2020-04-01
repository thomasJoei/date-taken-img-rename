package com.thojoeis;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

public class ImageRenamer {

    private static final String DATE_FORMAT = "yyyy-MM-dd_HHmmssS";
    private static final DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
    private static final String fileNameTemplate = "%s" + File.separator + "%s.%s";

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        // Create and set up the window.
        final JFrame frame = new JFrame("Images Renamer");

        // Display the window.
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set flow layout for the frame
        frame.getContentPane().setLayout(new FlowLayout());

        JButton button = new JButton("Choose images");
        button.addActionListener(e -> createFileChooser(frame));

        frame.getContentPane().add(button);
    }


    private static void createFileChooser(final JFrame frame) {
        JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & PNG Images", "jpg", "jpeg", "png");
        fileChooser.setFileFilter(filter);
        fileChooser.setMultiSelectionEnabled(true);

        // pop up an "Open File" file chooser dialog
        fileChooser.showOpenDialog(frame);

        File[] files = fileChooser.getSelectedFiles();
        Arrays.stream(files).forEach(ImageRenamer::renameFile);
    }

    private static void renameFile(File file) {
        try {
            String oldName = file.getName();
            String newAbsoluteFilename = getNewAbsoluteFilename(file);
            File newFile = new File(newAbsoluteFilename);
            if (file.renameTo(newFile)) {
                System.out.println(String.format("%s => %s", oldName, newFile.getName()));
            } else {
                System.out.println("File rename failed");
            }
        } catch (IOException | ImageProcessingException ex) {
            System.out.println("Could not get DateTimeOriginal metadata. ");
        }
    }

    private static String getNewAbsoluteFilename(File file) throws IOException, ImageProcessingException {
        Date imgDateTaken = getDateTaken(file);
        String formattedDate = formatter.format(imgDateTaken);

        String extension = getFileExtension(file);
        String dirPath = Optional.ofNullable(file.getParent()).orElse("");

        return String.format(fileNameTemplate, dirPath, formattedDate, extension);
    }

    private static Date getDateTaken(File imgFile) throws IOException, ImageProcessingException {
        Metadata metadata = ImageMetadataReader.readMetadata(imgFile);
        ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

        return directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }

}
