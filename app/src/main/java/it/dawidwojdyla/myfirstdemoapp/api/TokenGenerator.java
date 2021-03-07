package it.dawidwojdyla.myfirstdemoapp.api;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TokenGenerator {

    public static String generateToken() throws NoSuchAlgorithmException {

        String stringNumber = String.valueOf(generateNumber());
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(stringNumber.getBytes(StandardCharsets.UTF_8));
        return String.format("%032x", new BigInteger(1, md5.digest()) );
    }

    private static int generateNumber() {

        char[] digits = String.valueOf(System.currentTimeMillis() / 10000).toCharArray();
        int sum = 0;
        for (char d: digits) {
            sum += Integer.parseInt(String.valueOf(d));
        }
        return sum * 77 + 217;
    }
}
