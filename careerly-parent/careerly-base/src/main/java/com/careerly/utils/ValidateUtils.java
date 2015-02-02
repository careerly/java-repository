package com.careerly.utils;

/**
 * 实现描述：验证框架
 */

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {

    public final static int MAX_EMAIL_LENGTH = 50;
    public final static int MAX_ID_LENGTH = 50;
    public final static int MAX_MOBILE_LENGTH = 11;
    public final static int MAX_NAME_LENGTH = 30;
    public final static int MAX_PHONE_LENGTH = 20;

    private static final String TAIWAN_PERMIT_EXP = "^[a-zA-Z]([0-9]{10})$";
    private static final String HONG_KONG_PERMIT_EXP = "^[Ww]([0-9]{8})$";
    private static final String PASSPORT_PERMIT_EXP = "^[a-zA-Z]([0-9]{7,8})$";
    private static final String FAX_EXP = "(^(\\d{3,4}-)?\\d{7,8})$|^((1[0-9][0-9]\\d{8}$))";

    /**
     * 验证邮箱号码是否符合规范
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }
        String str = "^[a-zA-Z0-9.!#$%&amp;'*+\\-\\/=?\\^_`{|}~\\-]+@[a-zA-Z0-9\\-]+(?:\\.[a-zA-Z0-9\\-]+)*$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches() && email.length() < ValidateUtils.MAX_EMAIL_LENGTH;
    }

    /**
     * 验证身份证号是否符合规范
     *
     * @param IDCard
     * @return
     */
    public static boolean isIDCard(String IDCard) {
        if (StringUtils.isBlank(IDCard)) {
            return false;
        }
        return IDCardCheck.Verify(IDCard);
    }

    /**
     * 是否是手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNo(String mobiles) {
        if (StringUtils.isBlank(mobiles)) {
            return false;
        }
        Pattern p = Pattern.compile("^1\\d{10}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 是否是电话号码（手机或者座机）
     *
     * @param phone
     * @return
     */
    public static boolean isPhoneNo(String phone) {
        if (StringUtils.isBlank(phone)) {
            return false;
        }
        Pattern p = Pattern.compile("^[[0-9]-()]*$");
        Matcher m = p.matcher(phone);
        return m.matches() && phone.length() <= ValidateUtils.MAX_PHONE_LENGTH;
    }

    /**
     * 验证用户名是否符合规范
     *
     * @param userName
     * @return
     */
    public static boolean isValidUserName(String userName) {
        if (StringUtils.isBlank(userName)) {
            return false;
        }
        return userName.length() <= ValidateUtils.MAX_NAME_LENGTH;
    }

    /**
     * 验证拼音是否符合规范
     *
     * @param pinyin
     * @return
     */
    public static boolean isPinyin(String pinyin) {
        return true;
    }

    /**
     * 验证邮寄地址是否符合规范
     *
     * @param postAddress
     * @return
     */
    public static boolean isPostAddress(String postAddress) {
        return true;
    }

    /**
     * 验证邮编是否符合规范
     *
     * @param postCode
     * @return
     */
    public static boolean isPostCode(String postCode) {
        return true;
    }

    /**
     * 验证护照是否符合规范
     *
     * @param passport
     * @return
     */
    public static boolean isPassport(String passport) {
        if (StringUtils.isBlank(passport)) {
            return false;
        }
        return PatternUtils.isMatch(passport, PASSPORT_PERMIT_EXP);
    }

    /**
     * 验证港澳通行证是否符合规范
     *
     * @param hongKongAndMacauPermit
     * @return
     */
    public static boolean isHongKongAndMacauPermit(String hongKongAndMacauPermit) {
        if (StringUtils.isBlank(hongKongAndMacauPermit)) {
            return false;
        }
        return PatternUtils.isMatch(hongKongAndMacauPermit, HONG_KONG_PERMIT_EXP);
    }

    /**
     * 验证台胞证是否符合规范
     *
     * @param taiwanPermit
     * @return
     */
    public static boolean isTaiwanPermit(String taiwanPermit) {
        if (StringUtils.isBlank(taiwanPermit)) {
            return false;
        }
        return PatternUtils.isMatch(taiwanPermit, TAIWAN_PERMIT_EXP);
    }

    /**
     * 验证did电话
     *
     * @param didPhone
     * @return
     */
    public static boolean isDidPhone(String didPhone) {
        Pattern pattern = Pattern.compile("^0\\d{10,11}|0\\d{2,3}\\-\\d{7,8}$");
        Matcher m = pattern.matcher(didPhone);
        return m.matches();
    }

    /**
     * 验证是否是400电话
     *
     * @param fourHundredNo
     * @return
     */
    public static boolean isFourHundredPhone(String fourHundredNo) {
        Pattern pattern = Pattern.compile("^\\d{4}-\\d{3}-\\d{3}");
        Matcher m = pattern.matcher(fourHundredNo);
        return m.matches();
    }

    /**
     * 验证400电话里供应商电话格式
     *
     * @param realPhone
     * @return
     */
    public static boolean isSupplierRealPhone(String realPhone) {
        Pattern pattern = Pattern.compile("^1\\d{10}|0\\d{10,11}|0\\d{2,3}\\-\\d{7,8}$");
        Matcher m = pattern.matcher(realPhone);
        return m.matches();
    }


    /**
     * 验证传真
     *
     * @param fax
     * @return
     */
    public static boolean isFax(String fax) {
        if (StringUtils.isBlank(fax)) {
            return false;
        }
        return PatternUtils.isMatch(fax, FAX_EXP);
    }


}
