package kz.javalab.songslyricswebsite.entity.password;

import java.security.MessageDigest;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by PaperPlane on 12.08.2017.
 */
public class Password {
    private String hashedPassword;

    public Password() {
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void encodePassword(String unEncodedPassword) {
        hashedPassword = getEncodedPassword(unEncodedPassword);
    }

    public boolean matches(String unEncodedPassword) {
        String encodedPassword = getEncodedPassword(unEncodedPassword);
        return encodedPassword.equals(hashedPassword);
    }

    private String getEncodedPassword(String unEncodedPassword) {
        String result = new String();

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            try {
                messageDigest.update(unEncodedPassword.getBytes("UTF-8"));
                result = new BigInteger(1, messageDigest.digest()).toString(16);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }
}
