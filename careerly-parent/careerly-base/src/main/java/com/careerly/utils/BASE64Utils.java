package com.careerly.utils;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.nio.charset.Charset;

public class BASE64Utils {

    private final static BASE64Encoder base64Encoder = new BASE64Encoder();

    private final static BASE64Decoder base64Decoder = new BASE64Decoder();

    /**
     * base64编码
     *
     * @param text
     * @return
     */
    public static String encode(String text) {
        if (StringUtils.isBlank(text)) {
            return StringUtils.EMPTY;
        }
        return base64Encoder.encode(text.getBytes());
    }

    /**
     * base64编码
     *
     * @param text
     * @param charset
     * @return
     */
    public static String encode(String text, Charset charset) {
        if (StringUtils.isBlank(text)) {
            return StringUtils.EMPTY;
        }
        return base64Encoder.encode(text.getBytes(charset));
    }

    /**
     * base64编码
     *
     * @param text
     * @return
     */
    public static String encode(byte[] text) {
        if (ArrayUtils.isEmpty(text)) {
            return StringUtils.EMPTY;
        }
        return base64Encoder.encode(text);
    }

    /**
     * base64解密
     *
     * @param text
     * @return
     */
    public static String decode(String text) {
        if (StringUtils.isBlank(text)) {
            return StringUtils.EMPTY;
        }
        byte[] decode = null;
        try {
            decode = base64Decoder.decodeBuffer(text);
        } catch (IOException e) {
            //解密失败
        }
        if (decode == null) {
            return StringUtils.EMPTY;
        }
        return new String(decode);
    }
}
