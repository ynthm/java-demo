package com.ynthm.tools.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Map;
import java.util.stream.Collectors;

public class SecureHashUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecureHashUtil.class);

    /**
     * 指定字符集
     */
    private static String CHARSET = "UTF-8";

    /**
     * SHA-1 (Simplest one – 160 bits Hash)
     * SHA-256 (Stronger than SHA-1 – 256 bits Hash)
     * SHA-384 (Stronger than SHA-256 – 384 bits Hash)
     * SHA-512 (Stronger than SHA-384 – 512 bits Hash)
     */
    private static final String ALGORITHM_SHA1 = "SHA-1";

    private static final String ALGORITHM_SHA256 = "SHA-256";

    private static final String ALGORITHM_SHA512 = "SHA-512";

    private static final String ALGORITHM_MD5 = "MD5";

    private static final String ALGORITHM_HMACSHA256 = "HmacSHA256";

    public static final String ALGORITHM_SHA1PRNG = "SHA1PRNG";

    public static String sha1(String input) {
        return digest(ALGORITHM_SHA1, input);
    }

    public static String sha1(String input, byte[] salt) {
        return digest(ALGORITHM_SHA1, input, salt);
    }


    public static String sha256(String input) {
        return digest(input, ALGORITHM_SHA256);
    }

    public static String sha256(String input, byte[] salt) {
        return digest(ALGORITHM_SHA256, input, salt);
    }

    public static String sha512(String input) {
        return digest(input, ALGORITHM_SHA512);
    }

    public static String sha512(String input, byte[] salt) {
        return digest(ALGORITHM_SHA512, input, salt);
    }


    /**
     * 字符串md5编码
     *
     * @param input
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static String md5(String input) {
        return md5(input, 32);
    }

    public static String md5(String input, int type) {

        String digest = digest(ALGORITHM_MD5, input);

        if (type == 16) {
            return digest.substring(8, 24);
        }

        return null;
    }

    public static String md5(String input, byte[] salt) {
        return digest(ALGORITHM_MD5, input, salt);
    }

    public static String salt() {

        try {
            byte[] salt = getSalt();
            return bytes2hex(salt);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("No such algorithm exception" + e);
        } catch (NoSuchProviderException e) {
            LOGGER.error("No such provider Exception" + e);
        }
        return "";
    }

    public static byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
        //Always use a SecureRandom generator
        SecureRandom sr = SecureRandom.getInstance(ALGORITHM_SHA1PRNG, "SUN");
        //Create array for salt
        byte[] salt = new byte[16];
        //Get a random salt
        sr.nextBytes(salt);
        //return salt
        return salt;
    }

    public static String hmacSHA256(String data, String key) {
        try {
            return hmacSHA256(data.getBytes(CHARSET), key.getBytes(CHARSET));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("UnsupportedEncodingException.", e);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("no such algorithm.", e);
        } catch (InvalidKeyException e) {
            LOGGER.error("invalid key.", e);
        }

        return null;
    }

    public static String hmacSHA256(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec signingKey = new SecretKeySpec(key, ALGORITHM_HMACSHA256);
        Mac mac = Mac.getInstance(ALGORITHM_HMACSHA256);
        mac.init(signingKey);
        return byte2hex(mac.doFinal(data)).toLowerCase();

    }

    private static String digest(String algorithm, String input) {
        return digest(algorithm, input, null);
    }

    private static String digest(String algorithm, String input, byte[] salt) {
        if (StringUtils.isBlank(input)) {
            return "";
        }

        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            if (salt != null) {
                md.update(salt);
            }

            byte[] result = md.digest(input.getBytes(CHARSET));
            return bytes2hex(result);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("No such algorithm exception" + e);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Unsupported Encoding Exception" + e);
        }

        return "";
    }

    /**
     * 转十六进制
     *
     * @param bytes
     * @return
     */
    private static String bytes2hex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    private static String byte2hex(byte[] hash) {
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
}
