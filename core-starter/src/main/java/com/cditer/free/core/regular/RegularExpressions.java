package com.cditer.free.core.regular;

public class RegularExpressions {
    /**
     * 邮箱
     */
    public static final String email="^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    /**
     * 电话、座机
     */
    public static final String phone="^\\d{3}-\\d{8}|\\d{4}-\\d{7}$";

    /**
     * 电话、移动电话
     */
    public static final String mobile="^1\\d{10}$";

    /**
     * 电话、移动电话和座机
     */
    public static final String mobilePhone="^\\d{11,12}$";

    /**
     * QQ
     */
    public static final String qq="^[1-9][0-9]{4,}$";

    /**
     * 邮编
     */
    public static final String zipCode="^[1-9]\\d{5}(?!\\d)$";

    /**
     * 中文字符
     */
    public static final String chineseChar="[\\u4e00-\\u9fa5]";

    /**
     * 身份证
     */
    //public static final String idCard="^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
    public static final String idCard="^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";

    /**
     * 时间
     */
    public static final String date="([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))";

    /**
     *日期
     */
    public static final String longDate="^[1-3][0-9][0-9][0-9]-(?:(?:0[1-9])|(?:1[0-2]))-(?:(?:[0-2][1-9])|(?:[1-3][0-1]))$";
}
