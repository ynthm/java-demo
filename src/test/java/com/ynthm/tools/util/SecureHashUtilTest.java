package com.ynthm.tools.util;

import com.google.common.base.Stopwatch;
import org.bouncycastle.crypto.generators.OpenBSDBCrypt;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

class SecureHashUtilTest {

    @Test
    void sha1() {
        System.out.println(SecureHashUtil.sha1("Ethan"));
    }

    @Test
    void sha1WithSalt() throws NoSuchProviderException, NoSuchAlgorithmException {

        System.out.println(SecureHashUtil.salt());

        String test = "vt78EDbKZuTcg/CzCv0GXQ==";
        byte[] salt = Base64.getDecoder().decode(test);
        System.out.println(Base64.getEncoder().encodeToString(salt));
        System.out.println(SecureHashUtil.sha1("Ethan", salt));
    }

    @Test
    void sha256() {
    }

    @Test
    void sha256WithSalt() {
    }

    @Test
    void sha512() {
    }

    @Test
    void sha512WithSalt() {
    }

    @Test
    void md5() {
    }

    @Test
    void md51() {
    }

    @Test
    void md5WithSalt() {
    }

    @Test
    void getSalt() {
    }

    @Test
    void hmacSHA256() {
    }

    @Test
    void hmacSHA2561() {
    }

    @Test
    void hmacSHA1PBKDF2() throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = SecureHashUtil.getSalt();
        String s = SecureHashUtil.hmacsha1PBKDF2("123456", salt);
        System.out.println(SecureHashUtil.validatePBKDF2WithHmacSHA1("123456", s));

    }

    @Test
    void bsCrypt() throws NoSuchProviderException, NoSuchAlgorithmException, UnsupportedEncodingException {

        byte[] salt = SecureHashUtil.getSalt(16);
        String s1 = Hex.toHexString(salt);
        System.out.println(s1);
        byte[] decode = Hex.decode(s1);
        System.out.println(Hex.toHexString(decode));

        Stopwatch sw = Stopwatch.createStarted();
        //  Spring Security 3新加入的 BCrypt 算法 BCryptPasswordEncoder
        String generate = OpenBSDBCrypt.generate("123456".toCharArray(), salt, 4);
        System.out.println(generate);
        System.out.println(OpenBSDBCrypt.checkPassword(generate, "123456".toCharArray()));

        String s = SecureHashUtil.bCrypt("123456", salt);
        System.out.println(s);
        // 1 微秒 1000 纳秒  千分之一毫秒
        System.out.println(sw.elapsed(TimeUnit.MICROSECONDS));
        String s2 = SecureHashUtil.sCrypt("123123", salt);
        System.out.println(s2);

    }
}