package com.careerly.utils;

import java.text.DecimalFormat;

/**
 * 实现描述：数字转换工具类
 */
public class NumberUtils {

    private final static DecimalFormat decimalFormat = new DecimalFormat("###,###.##");

    /**
     * 将数字格式化为###,###的形式
     * 
     * @param amount
     * @return
     */
    public static String formatMoney(double amount) {
        try {
            return NumberUtils.decimalFormat.format(amount);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 将数字格式化为###,###的形式
     * 
     * @param amount
     * @return
     */
    public static String formatMoney(long amount) {
        try {
            return NumberUtils.decimalFormat.format(amount);
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean equalInteger(Integer before,Integer after){
        if(before == null && after == null){
            return true;
        }

        if(before == null || after == null){
            return false;
        }

        if(before.equals(after)){
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        String s = NumberUtils.decimalFormat.format(123);
        System.out.print(s);
    }
}
