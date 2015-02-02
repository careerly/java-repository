/*
 * Copyright 2012 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.careerly.utils;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Pattern;

/**
 * 实现描述：文本处理工具
 */
public class TextUtils {

    private static final Logger logger = LoggerFactory.getLogger(TextUtils.class);

    public static final String WHITESPACE = " ";

    /**
     * 繁体中文数字 转 阿拉伯数字
     *
     * @param text 要转换的字串
     * @return 转换后的字串
     */
    public static String convertTraditionalChineseNumber2ArabicNumber(String text) {
        if (StringUtils.isBlank(text)) {
            return StringUtils.EMPTY;
        }

        return StringUtils.replaceEach(text, new String[]{"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"},
                new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"});
    }

    /**
     * 阿拉伯数字 转 简体中文数字
     *
     * @param number 要转换的字串
     * @return 转换后的字串
     */
    public static String convertSimplifiedArabicNumber2ChineseNumber(int number) {
        return StringUtils.replaceEach(number + "", new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"},
                new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"});
    }

    /**
     * 字符串xss逃逸，Html页面用
     *
     * @param text 要处理的值
     * @return 转换后的值
     */
    public static String escapeHtml(String text) {
        return StringUtils.isBlank(text) ? StringUtils.EMPTY : StringEscapeUtils.escapeHtml(text);
    }

    /**
     * 字符串xss逃逸，javascript脚本用
     *
     * @param text 要处理的值
     * @return 转换后的值
     */
    public static String escapeJavaScript(String text) {
        return StringUtils.isBlank(text) ? StringUtils.EMPTY : StringEscapeUtils.escapeJavaScript(text);
    }

    /**
     * 转意非法字符，仅包括（英文字母，数字，汉字，空格）
     *
     * @param text
     * @return
     */
    public static String escapeNotNormalString(String text) {

        if (StringUtils.isBlank(text)) {
            return StringUtils.EMPTY;
        }
        return StringUtils.replaceEach(text, new String[]{"&", "<", ">", "\"", "\\", "\'"}, new String[]{"&amp;",
                "&lt;", "&gt;", "&quot;", "\\\\", "&quot;"});
    }

    public static String revertNotNormalString(String text) {

        if (StringUtils.isBlank(text)) {
            return StringUtils.EMPTY;
        }

        return StringUtils.replaceEach(text, new String[]{"&amp;", "&lt;", "&gt;", "&quot;", "\\\\", "&quot;"},
                new String[]{"&", "<", ">", "\"", "\\", "\'"});
    }


    public static String escapeNormalString(String text) {

        if (StringUtils.isBlank(text)) {
            return StringUtils.EMPTY;
        }

        return StringUtils.replaceEach(text, new String[]{"&lt;",
                "&gt;", "&quot;", "\\\\", "&quot;"}, new String[]{"<", ">", "\"", "\\", "\'"});
    }

    /**
     * 转意非法字符，仅包括（英文字母，数字，汉字，空格）
     *
     * @param text
     * @return
     */
    public static String reductionNotNormalString(String text) {

        if (StringUtils.isBlank(text)) {
            return StringUtils.EMPTY;
        }

        return StringUtils.replaceEach(text, new String[]{"&amp;", "&lt;", "&gt;", "&quot;", "\\\\", "&quot;", "[",
                "]"}, new String[]{"&", "<", ">", "\"", "\\", "\'", "<", ">"});
    }

    /**
     * 是否包含英文字母，数字，汉字，空格
     *
     * @param text
     * @return
     */
    public static boolean hasAnyNormalString(String text) {

        if (StringUtils.isBlank(text)) {
            return false;
        }

        for (int i = 0; i < text.length(); i++) {
            String str = String.valueOf(text.charAt(i));
            if (StringUtils.isNotBlank(str) && TextUtils.isNormalString(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查是否存在英文字符
     *
     * @param text
     * @return
     */
    public static boolean hasEnglishChar(String text) {

        if (StringUtils.isBlank(text)) {
            return false;
        }

        for (int i = 0; i < text.length(); i++) {
            if (TextUtils.isEnglishChar(text.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 模糊手机号码，隐藏号码中间5位，替换为*号
     *
     * @param telephone
     * @return
     */
    public static String blurMoblieNumber(String telephone) {
        if (StringUtils.isBlank(telephone)) {
            return StringUtils.EMPTY;
        }
        return telephone.substring(0, 3) + "****" + telephone.substring(7, 11);
    }

    /**
     * 限制字符串长度，超出部分截断并填充字符
     *
     * @param str
     * @param maxLength
     * @param tailPadding
     * @return
     */
    public static String limitString(String str, int maxLength, String tailPadding) {
        if (StringUtils.isBlank(str)) {
            return StringUtils.EMPTY;
        }
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength) + tailPadding;
    }

    /**
     * 判断一个字符是否为中文字符（不含标点符号）
     *
     * @param ch 要判断的字符
     * @return 如果为中文字符，返回true
     */
    public static boolean isChineseChar(char ch) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(ch);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A;
    }

    /**
     * 判断一个字符串中是否含有中文字符（不含标点符号）
     *
     * @param text
     * @return
     */
    public static boolean isChineseStr(String text) {
        if (StringUtils.isBlank(text)) {
            return false;
        }
        for (int i = 0; i < text.length(); i++) {
            if (!TextUtils.isChineseChar(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断一个字符是否是数字
     *
     * @param ch
     * @return
     */
    public static boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    /**
     * 判断一个字符是否为英文字符或者数字（a-zA-Z0-9）
     *
     * @param ch 要判断的字符
     * @return 如果为英文字符 或者数字，返回true
     */
    public static boolean isDigitOrEngilishChar(char ch) {
        return Character.isLowerCase(ch) || Character.isUpperCase(ch) || Character.isDigit(ch);
    }

    /**
     * 判断一个字符是否为英文字符（a-zA-Z）
     *
     * @param ch 要判断的字符
     * @return 如果为英文字符，返回true
     */
    public static boolean isEnglishChar(char ch) {
        return Character.isLowerCase(ch) || Character.isUpperCase(ch);
    }

    /**
     * 是否只包含英文字母，数字，汉字，空格
     *
     * @param text
     * @return
     */
    public static boolean isNormalString(String text) {

        if (StringUtils.isBlank(text)) {
            return false;
        }

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (!TextUtils.isChineseChar(ch) && !TextUtils.isEnglishChar(ch) && !TextUtils.isDigit(ch) && ch != ' '
                    && ch != ' ') {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断一个字符是否为英文标点或中文标点
     *
     * @param ch 要判断的字符
     * @return 如果为标点，返回true
     */
    public static boolean isPunctuationChar(char ch) {
        if (ch > '\u0020' && ch <= '\u002F') {
            return true;
        }
        if (ch >= '\u003A' && ch <= '\u0040') {
            return true;
        }
        if (ch >= '\u005B' && ch <= '\u0060') {
            return true;
        }
        if (ch >= '\u007B' && ch <= '\u007E') {
            return true;
        }
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(ch);
        return ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    /**
     * 将连续的空白字符转换为一个英文空格
     *
     * @param text 要处理的值
     * @return 转换后的值
     */
    public static String normalizeWhitespace(String text) {
        if (StringUtils.isBlank(text)) {
            return StringUtils.EMPTY;
        }

        return text.replaceAll("[ 　]+", TextUtils.WHITESPACE);
    }

    /**
     * 去除双字节
     *
     * @param text
     * @return
     */
    public static String removeDoubleByte(String text) {

        if (StringUtils.isBlank(text)) {
            return StringUtils.EMPTY;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < text.codePointCount(0, text.length()); i++) {
            char[] chars = Character.toChars(text.codePointAt(i));
            if (chars.length == 1) {
                stringBuilder.append(String.valueOf(chars));
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 去除特定情况下的空格
     *
     * @param text
     * @return
     */
    public static String removeUselessSpace(String text) {

        if (StringUtils.isBlank(text)) {
            return StringUtils.EMPTY;
        }

        StringBuilder sb = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                if (i > 0 && TextUtils.isDigitOrEngilishChar(text.charAt(i - 1)) && i + 1 < text.length()
                        && TextUtils.isDigitOrEngilishChar(text.charAt(i + 1))) {
                    sb.append(text.charAt(i));
                }
            } else {
                sb.append(text.charAt(i));
            }
        }
        return sb.toString();
    }

    /**
     * 除去特殊字符c2a0(unicode - '\u00A0')
     *
     * @param text
     * @return
     */
    public static String replaceC2A0(String text) {

        if (StringUtils.isBlank(text)) {
            return StringUtils.EMPTY;
        }

        StringBuilder sb = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '\u00A0') {
                sb.append('\u0020');
            } else {
                sb.append(text.charAt(i));
            }
        }
        return sb.toString();
    }

    /**
     * 将所有标点替换为replaceChar
     *
     * @param text        原字符串
     * @param replaceChar 要换的字符
     * @return 替换后的string
     */
    public static String replacePunctuation(String text, char replaceChar) {

        if (StringUtils.isBlank(text)) {
            return StringUtils.EMPTY;
        }

        StringBuilder sb = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (TextUtils.isPunctuationChar(c)) {
                sb.append(replaceChar);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 去除非法字符，仅包括（英文字母，数字，汉字，空格）
     *
     * @param text
     * @return
     */
    public static String swapNotNormalString(String text) {

        if (StringUtils.isBlank(text)) {
            return StringUtils.EMPTY;
        }

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < text.length(); i++) {
            String str = String.valueOf(text.charAt(i));
            if (TextUtils.isNormalString(str)) {
                buffer.append(str);
            }
        }
        return buffer.toString();
    }

    /**
     * 字符串xss转逸，Html页面用
     *
     * @param text 要处理的值
     * @return 转换后的值
     */
    public static String unescapeHtml(String text) {
        return StringUtils.isBlank(text) ? StringUtils.EMPTY : StringEscapeUtils.unescapeHtml(text);
    }

    /**
     * 字符串xss转逸，javascript脚本用
     *
     * @param text 要处理的值
     * @return 转换后的值
     */
    public static String unescapeJavaScript(String text) {
        return StringUtils.isBlank(text) ? StringUtils.EMPTY : StringEscapeUtils.unescapeJavaScript(text);
    }

    public static String urlDecodeGBK(String text) {

        String result = StringUtils.EMPTY;

        if (StringUtils.isBlank(text)) {
            return result;
        }
        try {
            result = URLDecoder.decode(text, "GBK");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static String urlDecodeUTF8(String text) {
        String result = StringUtils.EMPTY;

        if (StringUtils.isBlank(text)) {
            return result;
        }

        try {
            result = URLDecoder.decode(text, Consts.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static String urlEncodeGBK(String text) {

        String result = StringUtils.EMPTY;

        if (StringUtils.isBlank(text)) {
            return result;
        }
        try {
            result = URLEncoder.encode(text, "GBK");
        } catch (UnsupportedEncodingException e) {
            logger.error("urlEncodeGBK occur error, text {}, execption", text, e);
        }
        return result;
    }

    public static String urlEncodeUTF8(String text) {

        String result = StringUtils.EMPTY;

        if (StringUtils.isBlank(text)) {
            return result;
        }
        try {
            result = URLEncoder.encode(text, Consts.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            logger.error("urlEncodeUTF8 occur error, text {}, execption", text, e);
        }
        return result;
    }

    /**
     * 符号字符串转义
     *
     * @param text
     * @return
     */
    public static String escapeQuote(String text) {
        if (StringUtils.isBlank(text)) {
            return StringUtils.EMPTY;
        }
        return StringUtils.replace(StringUtils.replace(text, "\\", "\\\\"), "\"", "\\\"");
    }

    /**
     * 判断一个字符串，是否可以结构化，标准为它是否含有html标签和特殊字符
     *
     * @param inputString
     * @return
     */
    public static boolean validateStructureHtml(String inputString) {
        Pattern pattern = Pattern.compile("<[a-zA-Z]+[1-9]?[^><]*>|</[a-zA-Z]+[1-9]?>|\\&[a-zA-Z]{1,10};",
                Pattern.CASE_INSENSITIVE);
        return pattern.matcher(inputString).find();
    }

}
