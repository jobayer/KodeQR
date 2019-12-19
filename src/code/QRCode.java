package code;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class QRCode {

    String inputText;
    String fileName;
    File imageFile;

    // Default constructor
    public QRCode(String inputText) {
        this.inputText = inputText;
    }

    private static String getRandomName() {
        String ALPHANUMERICS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder builder = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            builder.append(ALPHANUMERICS.charAt(random.nextInt(ALPHANUMERICS.length())));
        }
        return builder.toString() + ".png";
    }

    // Generate QR code from the inputText
    public void generate() throws WriterException, IOException {

        fileName = getRandomName();

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(inputText, BarcodeFormat.QR_CODE, 320, 320);

        BufferedImage bufferedImage = new BufferedImage(bitMatrix.getWidth(), bitMatrix.getHeight(), BufferedImage.TYPE_INT_RGB);
        bufferedImage.createGraphics();

        Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, bitMatrix.getWidth(), bitMatrix.getHeight());
        graphics2D.setColor(Color.BLACK);

        for (int i = 0; i < bitMatrix.getWidth(); i++) {
            for (int j = 0; j < bitMatrix.getWidth(); j++) {
                if (bitMatrix.get(i, j)) {
                    graphics2D.fillRect(i, j, 1, 1);
                }
            }
        }

        ImageIO.write(bufferedImage, Const.IMG_FILE_FORMAT, Utils.mkFileToTmpDir(fileName));

        imageFile = new File(Utils.getTmpDir() + fileName);
    }

    public File getImageFile() {
        return imageFile;
    }
}
