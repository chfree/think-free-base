package com.cditer.free.core.regular;

public class MathRegularExpressions {
    /**
     * 正整数1+
     */
    public static final String positiveNumber="^[1-9]\\d*$";

    /**
     * 自然数 0+
     */
    public static final String naturalNumber ="^[0-9]\\d*$";

    /**
     * 负整数
     */
    public static final String negativeNumber="^-[1-9]\\d*$";

    /**
     * 整数
     */
    public static final String integer="^-?[1-9]\\d*$";

    /**
     * 带小数点或不带小数点的任意长度
     */
    public static final String numberDecimalMore="^((\\d+\\.\\d*)|(\\d+))$";

    /**
     * 带小数点或不带小数点,小数点后2位
     */
    public static final String numberDecimalTwo="^((\\d+\\.\\d{0,2})|(\\d+))$";

    /**
     * 带小数点任意长度
     */
    public static final String decimalMore="^(\\d+\\.\\d*)$";

    /**
     * 带小数点,小数点后2位
     */
    public static final String decimalTwo="^(\\d+\\.\\d{0,2})$";

    /**
     * (负数)带小数点或不带小数点的任意长度
     */
    public static final String negativeNumberDecimalMore="^-((\\d+\\.\\d*)|(\\d+))$";

    /**
     * (负数)带小数点或不带小数点,小数点后2位
     */
    public static final String negativeNumberDecimalTwo="^-((\\d+\\.\\d{0,2})|(\\d+))$";

    /**
     * (负数)带小数点任意长度
     */
    public static final String negativeDecimalMore="^-(\\d+\\.\\d*)$";

    /**
     * (负数)带小数点,小数点后2位
     */
    public static final String negativeDecimalTwo="^-(\\d+\\.\\d{0,2})$";

    /**
     * (负数或)带小数点或不带小数点的任意长度
     */
    public static final String negativeOrNumberDecimalMore="^-?((\\d+\\.\\d*)|(\\d+))$";

    /**
     * (负数或)带小数点或不带小数点,小数点后2位
     */
    public static final String negativeOrNumberDecimalTwo="^-?((\\d+\\.\\d{0,2})|(\\d+))$";

    /**
     * (负数或)带小数点任意长度
     */
    public static final String negativeOrDecimalMore="^-?(\\d+\\.\\d*)$";

    /**
     * (负数或)带小数点,小数点后2位
     */
    public static final String negativeOrDecimalTwo="^-?(\\d+\\.\\d{0,2})$";

    /**
     * 小数或数字、小数后N位
     * @param n
     * @return
     */
    public static final String getNumberDecimalN(int n){
        return "^((\\d+\\.\\d{0,"+n+"})|(\\d+))$";
    }


    /**
     * 小数、小数后N位
     * @param n
     * @return
     */
    public static final String getDecimalN(int n){
        return "^(\\d+\\.\\d{0,"+n+"})$";
    }

    /**
     * (负)小数、小数后N位
     * @param n
     * @return
     */
    public static final String getNegativeDecimalN(int n){
        return "^-(\\d+\\.\\d{0,"+n+"})$";
    }

    /**
     * (负)小数或数字、小数后N位
     * @param n
     * @return
     */
    public static final String getNegativeNumberDecimalN(int n){
        return "^-((\\d+\\.\\d{0,"+n+"})|(\\d+))$";
    }

    /**
     * (负或)小数、小数后N位
     * @param n
     * @return
     */
    public static final String getNegativeOrDecimalN(int n){
        return "^-(\\d+\\.\\d{0,"+n+"})$";
    }

    /**
     * (负或)小数或数字、小数后N位
     * @param n
     * @return
     */
    public static final String getNegativeOrNumberDecimalN(int n){
        return "^-((\\d+\\.\\d{0,"+n+"})|(\\d+))$";
    }
}
