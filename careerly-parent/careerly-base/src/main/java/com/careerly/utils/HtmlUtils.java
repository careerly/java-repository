package com.careerly.utils;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtils {

    private final static String regxpForHtml = "<([^>]*)>"; // 过滤所有以<开头以>结尾的标签
    
    private final static String regxpForImgTag = "<\\s*img\\s+([^>]*)\\s*>"; // 找出IMG标签
  
    private final static String regxpForImaTagSrcAttrib = "src=\"([^\"]+)\""; // 找出IMG标签的SRC属性
    public final static String newLineSign = "\r\n";

    public final static String htmlNewLine = "<br/>";

    /**
     * 
     * 基本功能：替换标记以正常显示
     * <p>
     * 
     * @param input
     * @return String
     */  
    public String replaceTag(String input) {   
        if (!hasSpecialChars(input)) {   
            return input;   
        }   
        StringBuffer filtered = new StringBuffer(input.length());   
        char c;   
        for (int i = 0; i <= input.length() - 1; i++) {   
            c = input.charAt(i);   
            switch (c) {   
            case '<':   
                filtered.append("&lt;");   
                break;   
            case '>':   
                filtered.append("&gt;");   
                break;   
            case '"':   
                filtered.append("&quot;");   
                break;   
            case '&':   
                filtered.append("&amp;");   
                break;   
            default:   
                filtered.append(c);   
            }   
  
        }   
        return (filtered.toString());   
    }   
  
    /**
     * 
     * 基本功能：判断标记是否存在
     * <p>
     * 
     * @param input
     * @return boolean
     */  
    public boolean hasSpecialChars(String input) {   
        boolean flag = false;   
        if ((input != null) && (input.length() > 0)) {   
            char c;   
            for (int i = 0; i <= input.length() - 1; i++) {   
                c = input.charAt(i);   
                switch (c) {   
                case '>':   
                    flag = true;   
                    break;   
                case '<':   
                    flag = true;   
                    break;   
                case '"':   
                    flag = true;   
                    break;   
                case '&':   
                    flag = true;   
                    break;   
                }   
            }   
        }   
        return flag;   
    }   
  
    /**
     * 
     * 基本功能：过滤所有以"<"开头以">"结尾的标签
     * <p>
     * 
     * @param str
     * @return String
     */  
    public static String filterHtml(String str) { 
        if(StringUtils.isBlank(str)) return "";
        Pattern pattern = Pattern.compile(regxpForHtml);   
        Matcher matcher = pattern.matcher(str);   
        StringBuffer sb = new StringBuffer();   
        boolean result1 = matcher.find();   
        while (result1) {   
            matcher.appendReplacement(sb, "");   
            result1 = matcher.find();   
        }   
        matcher.appendTail(sb);   
        return sb.toString();   
    }   
  
    /**
     * 
     * 基本功能：过滤指定标签
     * <p>
     * 
     * @param str
     * @param tag 指定标签
     * @return String
     */  
    public static String fiterHtmlTag(String str, String tag) {   
        String regxp = "<\\s*" + tag + "\\s+([^>]*)\\s*>";   
        Pattern pattern = Pattern.compile(regxp);   
        Matcher matcher = pattern.matcher(str);   
        StringBuffer sb = new StringBuffer();   
        boolean result1 = matcher.find();   
        while (result1) {   
            matcher.appendReplacement(sb, "");   
            result1 = matcher.find();   
        }   
        matcher.appendTail(sb);   
        return sb.toString();   
    }   
  
    /**
     * 
     * 基本功能：替换指定的标签
     * <p>
     * 
     * @param str
     * @param beforeTag 要替换的标签
     * @param tagAttrib 要替换的标签属性值
     * @param startTag 新标签开始标记
     * @param endTag 新标签结束标记
     * @return String
     * @如：替换img标签的src属性值为[img]属性值[/img]
     */  
    public static String replaceHtmlTag(String str, String beforeTag,   
            String tagAttrib, String startTag, String endTag) {   
        String regxpForTag = "<\\s*" + beforeTag + "\\s+([^>]*)\\s*>";   
        String regxpForTagAttrib = tagAttrib + "=\"([^\"]+)\"";   
        Pattern patternForTag = Pattern.compile(regxpForTag);   
        Pattern patternForAttrib = Pattern.compile(regxpForTagAttrib);   
        Matcher matcherForTag = patternForTag.matcher(str);   
        StringBuffer sb = new StringBuffer();   
        boolean result = matcherForTag.find();   
        while (result) {   
            StringBuffer sbreplace = new StringBuffer();   
            Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag   
                    .group(1));   
            if (matcherForAttrib.find()) {   
                matcherForAttrib.appendReplacement(sbreplace, startTag   
                        + matcherForAttrib.group(1) + endTag);   
            }   
            matcherForTag.appendReplacement(sb, sbreplace.toString());   
            result = matcherForTag.find();   
        }   
        matcherForTag.appendTail(sb);   
        return sb.toString();   
    } 
    
    public static String transHtml2Text(String content){
        if(content == null) return "";
        //content = Jsoup.clean(content, Whitelist.basic());
        return Jsoup.clean(content, Whitelist.simpleText());
    }
    

    /**
     * 过滤掉字串中的空白，回车，换行，间隔等特殊字符
     * 
     * @param content
     * @return
     */
    public static String removeSpecilChar(String content) {
        String regex = "\\s*|\n|\r|\t";
        String newString = "";
        return replaceContent(regex, content, newString);
    }

    public static String replaceContent(String regex, String content, String newString) {
        String result = content;
        if (null != content) {
            Pattern pat = Pattern.compile(regex);
            Matcher mat = pat.matcher(content);
            result = mat.replaceAll(newString);
        }
        return result;
    }

    /**
     * 统一回车换行的格式为 \r\n
     * 
     * @param content
     * @return
     */
    public static String formatNewLine(String content) {
        String regex = "[\\r\\n\\t]+";
        return replaceContent(regex, content, newLineSign);
    }

    /**
     * 统一回车换行的格式为 <br/>
     * `
     * 
     * @param content
     * @return
     */
    public static String formatHtmlNewLine(String content) {
        String regex = "[\\r\\n\\t]+";
        return replaceContent(regex, content, htmlNewLine);
    }

    /**
     * 手机号码隐藏处理
     * 
     * @param content
     * @return
     */
    public static String encryptionPhoneNumber(String content) {
        String regex = "[^0-9a-z](1[358][0-9]|14[57])[0-9]{8}([^0-9a-z\r\n]|$)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        StringBuffer sb = new StringBuffer();
        boolean result = matcher.find();
        while (result) {
            String phone = matcher.group(0);
            if (phone.indexOf(".") == -1) {
                if (phone.contains("\\")) {
                    phone = phone.replaceAll("\\\\", "\\\\\\\\");
                }
                String subPhone = phone.substring(4, phone.length() - 5);
                phone = phone.replace(subPhone, "****");
                matcher.appendReplacement(sb, phone);
            }
            result = matcher.find();
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 证件号码隐藏处理
     * 
     * @param content
     * @return
     */
    public static String encryptionCardNumber(String content) {
        String regex = "[^0-9a-z](1[1-5]|2[1-3]|3[1-7]|4[1-6]|5[0-4]|6[1-5]|8[12])[0-9]{4}(19[0-9]{2}|200[0-9]|201[0-4])(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])[0-9]{3}[0-9x](?=([^0-9a-z\r\n]|$))";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        StringBuffer sb = new StringBuffer();
        boolean result = matcher.find();
        while (result) {
            String cardNumber = matcher.group(0);
            if (cardNumber.contains("\\")) {
                cardNumber = cardNumber.replaceAll("\\\\", "\\\\\\\\");
            }
            String subCardNumber = cardNumber.substring(5, cardNumber.length() - 4);
            cardNumber = cardNumber.replace(subCardNumber, "***********");
            matcher.appendReplacement(sb, cardNumber);
            result = matcher.find();
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * email 隐藏处理
     * 
     * @param content
     * @return
     */
    public static String encryptionEmail(String content) {
        String regex = "[^0-9a-z][0-9a-z_.]+@[0-9a-z]+\\.(com|net|cn)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        StringBuffer sb = new StringBuffer();
        boolean result = matcher.find();
        while (result) {
            String email = matcher.group(0);
            if (email.contains("\\")) {
                email = email.replaceAll("\\\\", "\\\\\\\\");
            }
            String subEmail = email.substring(0, email.indexOf("@"));
            int subEmailLength = subEmail.length();
            if (subEmailLength > 3) {
                subEmail = subEmail.substring(4, subEmailLength);
            } else {
                subEmail = subEmail.substring(1, subEmailLength);
            }
            email = email.replace(subEmail, generateEncryptionChar(subEmail.length()));
            matcher.appendReplacement(sb, email);
            result = matcher.find();
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String generateEncryptionChar(int length) {
        StringBuffer encryptionChar = new StringBuffer();
        for (int i = 0; i < length; i++) {
            encryptionChar.append("*");
        }
        return encryptionChar.toString();
    }
}
