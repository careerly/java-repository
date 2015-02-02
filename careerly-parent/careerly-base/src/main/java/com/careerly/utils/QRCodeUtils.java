package com.careerly.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 实现描述：二维码生成和解析
 *
 * @author zhangzixin
 * @version v1.0.0
 * @see
 * @since 14-5-28下午3:18
 */
public class QRCodeUtils {

    private static Logger logger = LoggerFactory.getLogger(QRCodeUtils.class);

    private static final String CODE_FORMAT = "UTF-8";

    /**
     * 生成普通二维码
     *
     * @param contents
     * @param width
     * @param height
     */
    public static File encodePR(String contents, int width, int height) {
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        // 指定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 1);
        // 指定编码格式
        hints.put(EncodeHintType.CHARACTER_SET, CODE_FORMAT);
        try {
            File tempFile = FileUtils.createTempFile(UUID.randomUUID().toString() + ".jpg");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);
            MatrixToImageWriter.writeToStream(bitMatrix, "jpg", new FileOutputStream(tempFile));
            return tempFile;
        } catch (Exception e) {
            logger.error(String.format("encodePR error contents:%s", contents), e);
            return null;
        }
    }

    /**
     * 解密二维码
     * @param url
     * @return
     */
    public static String decryptPR(String url){
        BufferedImage bufferedImage = null;
        try {
            URL httpUrl = new URL(url);
            bufferedImage = ImageIO.read(httpUrl.openStream());
        } catch (IOException e) {
            logger.error(String.format("read img:%s error",url),e);
            return null;
        }
        LuminanceSource lus = null;
        lus = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bm = new BinaryBitmap(new HybridBinarizer(lus));
        Map<DecodeHintType, String> hints = new HashMap<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, CODE_FORMAT);
        try {
            Result result = new MultiFormatReader().decode(bm, hints);
            return result.getText();
        } catch (NotFoundException e) {
            logger.error(String.format("read img:%s error", url), e);
            return null;
        }
    }


}
