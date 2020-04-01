package com.thojoeis;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ImageRenamer extends Application {

    private static final String TITLE = "Image Renamer";
    private static final String DATE_FORMAT = "yyyy-MM-dd_HHmmssS";
    private static final DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
    private static final String fileNameTemplate = "%s" + File.separator + "%s.%s";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG & PNG Images", "*.jpg", "*.jpeg", "*.png")
        );

        Optional<List<File>> selectedFiles = Optional.ofNullable(fileChooser.showOpenMultipleDialog(primaryStage));
        selectedFiles.ifPresent(this::processFiles);

        Platform.exit();
    }

    private void processFiles(List<File> selectedFiles) {
        long fileRenamedCounter = selectedFiles.stream().filter(ImageRenamer::renameFile).count();
        long fileNotRenamedCounter = selectedFiles.size() - fileRenamedCounter;

        Alert closingPopup = getClosingPopup(fileRenamedCounter, fileNotRenamedCounter);
        closingPopup.showAndWait();
    }

    private Alert getClosingPopup(long fileRenamedCounter, long fileNotRenamedCounter) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(TITLE);
        alert.setHeaderText("Renaming completed !");
        String alertText = String.format("%d Image(s) have been renamed.", fileRenamedCounter);

        if (fileNotRenamedCounter != 0) {
            alertText = String.format("%s \n%d Image(s) could not been renamed.", alertText, fileNotRenamedCounter);
        }

        alert.setContentText(alertText);
        return alert;
    }

    private static boolean renameFile(File file) {
        try {
            String oldName = file.getName();
            String newAbsoluteFilename = getNewAbsoluteFilename(file);
            File newFile = new File(newAbsoluteFilename);

            if (file.renameTo(newFile)) {
                System.out.println(String.format("%s => %s", oldName, newFile.getName()));
                return true;
            } else {
                System.out.println("File rename failed");
            }
        } catch (IOException | ImageProcessingException ex) {
            System.out.println("Could not get DateTimeOriginal metadata. ");
        }
        return false;
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
        ExifSubIFDDirectory directory = Optional.ofNullable(metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class))
                .orElseThrow(() -> new ImageProcessingException("No ExifSubIFDDirectory"));

        return directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }

}
