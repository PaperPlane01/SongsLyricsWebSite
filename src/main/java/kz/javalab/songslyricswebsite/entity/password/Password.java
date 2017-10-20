package kz.javalab.songslyricswebsite.entity.password;

import java.security.MessageDigest;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

/**
 * This class represents password and contains methods for encoding password
 * using MD5 algorithm and for matching the password.
 */
public class Password {
    /**
     * Hashed password encoded with MD5 algorithm.
     */
    private String hashedPassword;

    /**
     * Constructs <Code>Password</Code> instance.
     */
    public Password() {
    }

    /**
     * Returns hashed password.
     * @return Hashed password.
     */
    public String getHashedPassword() {
        return hashedPassword;
    }

    /**
     * Modifies hashed password.
     * @param hashedPassword New hashed password to be set.
     */
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    /**
     * Encodes password using MD5 algorithm.
     * @param notEncodedPassword Password which is to be encoded.
     */
    public void encodePassword(String notEncodedPassword) {
        hashedPassword = getEncodedPassword(notEncodedPassword);
    }

    /**
     * Checks if the specified not encoded password matches to <Code>hashedPassword</Code> field of this
     * <Code>Password</Code> instance.
     * Method encodes given password using MD5 algorithm and then compares it with <Code>hashedPassword</Code>
     * field of this <Code>Password</Code> instance.
     * @param notEncodedPassword Not encoded password which is to be checked.
     * @return <Code>True</Code> if the specified password equals to <Code>hashedPassword</Code> after encoding,
     * <Code>False</Code> if not.
     */
    public boolean matches(String notEncodedPassword) {
        String encodedPassword = getEncodedPassword(notEncodedPassword);
        return encodedPassword.equals(hashedPassword);
    }

    /**
     * Encodes given password with MD5 algorithm.
     * @param notEncodedPassword Password to be encoded.
     * @return Encoded password.
     */
    private String getEncodedPassword(String notEncodedPassword) {
        String result = "";

        String algorithm = "MD5";
        String charSet = "UTF-8";
        int signum = 1;
        int radix = 16;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(notEncodedPassword.getBytes(charSet));
            result = new BigInteger(signum, messageDigest.digest()).toString(radix);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
        }

        return result;
    }
}
