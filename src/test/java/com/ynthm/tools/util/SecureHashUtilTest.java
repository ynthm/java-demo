package com.ynthm.tools.util;

import com.fasterxml.jackson.databind.ser.Serializers;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

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
}