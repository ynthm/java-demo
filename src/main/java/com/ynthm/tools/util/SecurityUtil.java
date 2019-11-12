package com.ynthm.tools.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.stream.Collectors;

public class SecurityUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUtil.class);

    /**
     * 指定字符集
     */
    private static String CHARSET = "UTF-8";

    private static final String ALGORITHM_SHA1 = "SHA-1";

    private static final String ALGORITHM_SHA256 = "SHA-256";

    private static final String ALGORITHM_SHA512 = "SHA-512";

    private static final String ALGORITHM_MD5 = "MD5";

    private static final String ALGORITHM_HMACSHA256 = "HmacSHA256";

    public static String sha1(String input) {
        return SHA(input, ALGORITHM_SHA1);
    }

    public static String sha256(String input) {
        return SHA(input, ALGORITHM_SHA256);
    }

    public static String sha512(String input) {
        return SHA(input, ALGORITHM_SHA512);
    }

    private static String SHA(String input, String type) {
        if (StringUtils.isBlank(input)) {
            return "";
        }

        try {
            MessageDigest mDigest = MessageDigest.getInstance(type);
            byte[] result = mDigest.digest(input.getBytes(CHARSET));
            if (result == null) {
                return "";
            }
            // 十六进制String
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < result.length; i++) {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("No such algorithm exception" + e);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Unsupported Encoding Exception" + e);
        }

        return "";
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

        String hex = null;

        try {
            byte[] hash = MessageDigest.getInstance(ALGORITHM_MD5).digest(input.getBytes(CHARSET));
            hex = byte2hex(hash);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("No such algorithm exception" + e);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Unsupported Encoding Exception" + e);
        }

        if (type == 16) {
            return hex.substring(8, 24);
        }

        return hex;
    }

    public static String md5()
    {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM_MD5);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static String getSignMsg(Map<String, String> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        String key = "";

        for (Map.Entry<String, String> entry : map.entrySet()) {
            key = entry.getKey();
            sb.append(key).append('=').append(map.get(key)).append('&');
        }

        if (sb.indexOf("&") > -1)
            sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static String getSignMsg(int type, Map<String, String> map) {
        if (map == null) {
            return "";
        }
        String crypto = map.entrySet().stream().map(e -> e.getKey() + '=' + e.getValue())
                .collect(Collectors.joining("&"));

        return crypto;
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
