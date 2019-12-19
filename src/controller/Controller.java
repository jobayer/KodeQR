package controller;

import code.Const;
import code.QRCode;
import code.Utils;
import com.google.zxing.WriterException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Controller {

    @FXML
    JFXTextArea taInputText;

    @FXML
    Text txtAppName;

    @FXML
    ImageView qrImgView;

    @FXML
    JFXButton btnGenerateQRCode;
    @FXML
    JFXButton btnGenerateQRCodeAgain;
    @FXML
    JFXButton btnSaveAs;
    @FXML
    JFXButton btnCloseApp;
    @FXML
    JFXButton btnInputText;

    @FXML
    AnchorPane anchorPaneBtnHolder;
    @FXML
    Pane paneInputBtn;
    @FXML
    Pane paneTextArea;
    @FXML
    Pane paneImageView;

    @FXML
    Label developerName;
    @FXML
    Label appSourceUrl;

    QRCode qrCode;

    boolean isPaneImgViewInFront = false;
    boolean isPaneInputBtnInFront = true;

    ArrayList<JFXButton> jfxButtons;

    @FXML
    private void onButtonDefaultEvent(ActionEvent actionEvent) throws IOException, WriterException {
        if (actionEvent.getSource() == btnGenerateQRCode) {
            execBtnGenerateQRCodeEventAction();
        } else if (actionEvent.getSource() == btnGenerateQRCodeAgain) {
            execBtnGenerateQRCodeAgainEventAction();
        } else if (actionEvent.getSource() == btnSaveAs) {
            Utils.saveFileToDir((Stage) btnSaveAs.getScene().getWindow(), qrCode.getImageFile());
        } else if (actionEvent.getSource() == btnCloseApp) {
            Utils.exitApp((Stage) btnCloseApp.getScene().getWindow());
        } else if (actionEvent.getSource() == btnInputText) {
            execBtnInputTxtActionEvent();
        }
    }

    @FXML
    private void onMouseClickedEvent(MouseEvent mouseEvent) throws IOException, URISyntaxException {
        if (mouseEvent.getSource() == developerName) {
            Utils.openUrl(Const.DEVELOPER_URL);
        } else if (mouseEvent.getSource() == appSourceUrl) {
            Utils.openUrl(Const.APP_SOURCE_URL);
        } else if (mouseEvent.getSource() == txtAppName) {
            if (!isPaneInputBtnInFront) {
                paneInputBtn.toFront();
                Utils.restoreAnchorPaneStyle(anchorPaneBtnHolder, jfxButtons, txtAppName);
            }
        }
    }

    private void execBtnGenerateQRCodeEventAction() throws IOException, WriterException {
        generateQRCode();
        paneImageView.toFront();
        initBtnArray();
        new Thread(() -> {
            Utils.changeAnchorPaneStyle(anchorPaneBtnHolder, jfxButtons, txtAppName);
            btnGenerateQRCode.setDisable(true);
            btnGenerateQRCodeAgain.setDisable(false);
            btnSaveAs.setDisable(false);
        }).start();
        isPaneImgViewInFront = true;
    }

    private void execBtnGenerateQRCodeAgainEventAction() {
        paneTextArea.toFront();
        if (!taInputText.getText().isEmpty()) taInputText.selectAll();
        taInputText.requestFocus();
        new Thread(() -> {
            btnGenerateQRCodeAgain.setDisable(true);
            btnGenerateQRCode.setDisable(false);
            btnSaveAs.setDisable(true);
            Utils.deleteFile(qrCode.getImageFile());
        }).start();
    }

    private void execBtnInputTxtActionEvent() {
        paneTextArea.toFront();
        initBtnArray();
        Utils.changeAnchorPaneStyle(anchorPaneBtnHolder, jfxButtons, txtAppName);
        taInputText.requestFocus();
        Utils.watchTxtArea(taInputText, btnGenerateQRCode);
        isPaneInputBtnInFront = false;
    }

    private void initBtnArray() {
        jfxButtons = new ArrayList<>();
        jfxButtons.add(btnGenerateQRCode);
        jfxButtons.add(btnGenerateQRCodeAgain);
        jfxButtons.add(btnSaveAs);
        jfxButtons.add(btnCloseApp);
    }

    // Generate QR code and setup rests related to QR code creation & display process
    private void generateQRCode() throws IOException, WriterException {
        if (!taInputText.getText().isEmpty()) {
            qrCode = new QRCode(taInputText.getText());
            qrCode.generate();
            Image qrImage = new Image(qrCode.getImageFile().toURI().toString());
            qrImgView.setImage(qrImage);
        }
    }

}
