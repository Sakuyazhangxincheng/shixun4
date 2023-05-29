package com.example.userservice.util;

import java.security.SecureRandom;
import java.util.Random;

public class VerCodeGenerateUtil {
    //邮箱字符串提取，去除了容易混淆的几个字符比如0,o~
    private static final String SYMBOLS = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final Random RANDOM = new SecureRandom();

    public static String getVerCode() {
        char[] nonceChars = new char[4];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }
}
