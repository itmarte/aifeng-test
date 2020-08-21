package com.aifeng.highAva.spring.regexp;

import java.util.regex.Pattern;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/4/8 14:57
 */
public class RegexpTest {

    public static void main(String[] args) {
        String rep = "^[^-:/][-^,\\/:().'+\\?A-Za-z0-9\\s]+$";
        Pattern pattern = Pattern.compile(rep);
        System.out.println(pattern.matcher(":klasjfdljlsadjf").find());
        System.out.println(pattern.matcher("-klasjfdljlsadjf").find());
        System.out.println(pattern.matcher("/klasjfdljlsadjf").find());
        System.out.println(pattern.matcher("k2121:)-/lasjfdljlsadjf").find());
    }
}
