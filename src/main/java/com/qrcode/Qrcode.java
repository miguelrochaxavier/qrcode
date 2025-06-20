package com.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;

public class Qrcode {

    public String link;
    public int width = 350;
    public int height = 350;
    public String fileFormat = "qrrr.png";

    public void getLink() {
        System.out.println("Enter Qrcode Link:");
        Scanner sc = new Scanner(System.in);
        link = sc.nextLine();
    }

    public void getGenerateQRcode() {
        try {
            if (link == null || link.isEmpty()) {
                System.out.println("Error! There is no QR code link.");
                return;
            }

            Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(link, BarcodeFormat.QR_CODE, width, height, hints);

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int cor = bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF;
                    image.setRGB(x, y, cor);
                }
            }

            File arquivo = new File(fileFormat);
            ImageIO.write(image, "PNG", arquivo);
            System.out.println("QR Code generated in : " + arquivo.getAbsolutePath());

        } catch (WriterException | IOException e) {
            System.out.println("Error! QR Code is not generated.");
            e.printStackTrace();
        }
    }
}