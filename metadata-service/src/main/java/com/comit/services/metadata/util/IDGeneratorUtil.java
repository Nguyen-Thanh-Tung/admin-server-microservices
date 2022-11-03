package com.comit.services.metadata.util;

import java.security.SecureRandom;

public class IDGeneratorUtil {
    private static final String CHAR_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGIT = "0123456789";
    private static final int ID_SIZE = 16;


    private static final SecureRandom random = new SecureRandom();

    public static String gen() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < ID_SIZE; i++) {
            if (i % 2 == 0) {
                int index = random.nextInt(CHAR_LOWERCASE.length());
                result.append(CHAR_LOWERCASE.charAt(index));
            } else {
                int index = random.nextInt(DIGIT.length());
                result.append(DIGIT.charAt(index));
            }
        }
        return result.toString();
    }
}
