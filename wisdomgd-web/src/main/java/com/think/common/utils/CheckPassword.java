package com.think.common.utils;

/**
 * @description: 密码强度校验
 * 1) 密码控制只能输入字母、数字、特殊符号(~!@#$%^&*()_+[]{}|\;:'",./<>?)
 * 2) 长度 8-30 位，必须包括大小写字母、数字、特殊符号中的3种
 * 3) 密码不能包含用户名信息
 * Created by karma on 2022/7/5.
 */
public class CheckPassword {

    //数字
    public static final String REG_NUMBER = ".*\\d+.*";
    //小写字母
    public static final String REG_UPPERCASE = ".*[A-Z]+.*";
    //大写字母
    public static final String REG_LOWERCASE = ".*[a-z]+.*";
    //特殊符号(~!@#$%^&*()_+|<>,.?/:;'[]{}\)
    public static final String REG_SYMBOL = ".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*";

    public static boolean checkPasswordRule(String password, String userName){

        //密码为空及长度大于8位小于30位判断
        if (password == null || password.length() <8 || password.length()>30)
            return false;
        int i = 0;
        if (password.matches(CheckPassword.REG_NUMBER))
            i++;
        if (password.matches(CheckPassword.REG_LOWERCASE))
            i++;
        if (password.matches(CheckPassword.REG_UPPERCASE))
            i++;
        if (password.matches(CheckPassword.REG_SYMBOL))
            i++;
        boolean flag = password.equals(userName);
        if (i < 3 || flag)
            return false;
        return true;
    }

    public static void main(String[] args) {
        boolean flag = checkPasswordRule("320124Zsr", "admin");
        System.out.println("flag ===" + flag);
    }
}
