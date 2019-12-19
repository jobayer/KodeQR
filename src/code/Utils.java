package code;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;

public class Utils {

    // Make new file to system's temp directory
    public static File mkFileToTmpDir(String fileName) {
        String dirName = System.getProperty(Const.TMP_DIR_TAG) + Const.APP_TMP_DIR;
        File tmpDir = new File(dirName);
        if (!tmpDir.exists()) //noinspection ResultOfMethodCallIgnored
            tmpDir.mkdir();
        return new File(dirName + fileName);
    }

    // Return system's temp directory with postfix directory named "KodeQR"
    public static String getTmpDir() {
        return System.getProperty(Const.TMP_DIR_TAG) + Const.APP_TMP_DIR;
    }

    // Save qr image file to user specified directory with user specified name
    public static void saveFileToDir(Stage stage, File file) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(Const.FILE_CHOOSER_TITLE);
        fileChooser.setInitialFileName(file.getName());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(Const.FILE_CHOOSER_TXT_DES, Const.IMG_FILE_EXTENSION));
        File destFile = fileChooser.showSaveDialog(stage);
        new Thread(() -> {
            try {
                Files.copy(file.toPath(), destFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Delete a file
    public static void deleteFile(File file) {
        new Thread(() -> {
            try {
                Files.delete(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Exit app
    public static void exitApp(Stage currentStage) {
        currentStage.close();
    }

    // Watch TextArea & enable or disable a specific JFX button
    public static void watchTxtArea(JFXTextArea textArea, JFXButton jfxButton) {
        textArea.textProperty().addListener((observableValue, s, t1) -> {
            if (!t1.isEmpty()) {
                jfxButton.setDisable(false);
            } else jfxButton.setDisable(true);
        });
    }

    // Open URL passed to the parameter
    public static void openUrl(String url) throws URISyntaxException, IOException {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(new URI(url));
        }
    }

    // Change anchor pane & it's component style
    public static void changeAnchorPaneStyle(AnchorPane anchorPane, ArrayList<JFXButton> buttonLists, Text txtAppName) {
        new Thread(() -> {
            anchorPane.setStyle(Const.CHANGED_ANCHOR_STYLE);
            txtAppName.setStyle(Const.CHANGED_APP_NAME_STYLE);
            for (JFXButton buttonList : buttonLists) {
                buttonList.setTextFill(Color.WHITE);
            }
        }).start();
    }

    // Restore anchor pane & it's component style as it was at the app launch time
    public static void restoreAnchorPaneStyle(AnchorPane anchorPane, ArrayList<JFXButton> buttonLists, Text text) {
        new Thread(() -> {
            anchorPane.setStyle(Const.RESTORED_ANCHOR_STYLE);
            text.setStyle(Const.RESTORED_APP_NAME_STYLE);
            for (JFXButton buttonList : buttonLists) {
                buttonList.setTextFill(Color.BLACK);
            }
        }).start();
    }

}
