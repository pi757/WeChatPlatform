package com.snail.officialaccount.util.mp;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * @author YuanChong
 * @create 2019-07-07 12:06
 * @desc
 */
public class JSApiUtil {

    public static String generateSignature(String data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Formatter formatter = new Formatter();
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(data.getBytes("UTF-8"));
            byte[] digest = crypt.digest();
            for (byte b : digest) {
                formatter.format("%02x", b);
            }
            String signature = formatter.toString();
            return signature;
        }finally {
            if(formatter != null) {
                formatter.close();
            }
        }
    }
}
