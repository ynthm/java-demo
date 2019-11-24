package com.ynthm.tools.util;

import org.junit.jupiter.api.Test;

import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import static org.junit.jupiter.api.Assertions.*;

class RSAUtilTest {

    @Test
    void createKey() {
    }

    @Test
    void aesEncrypt() {

        String content = "hello,您好";
        String key = "sde@5f98H*^hsff%dfs$r344&df8543*er";
        String s = RSAUtil.aesEncrypt(content, key);
        System.out.println(s);

        System.out.println(RSAUtil.aesDecrypt(s, key));
    }

    @Test
    void aesDecrypt() throws UnsupportedEncodingException {

        String content = "hello,您好";
        String key = "sde@5f98H*^hsff%dfs$r344&df8543*er";

        Key aesKey = RSAUtil.getAesKey(key);
        byte[] bytes = RSAUtil.aesEncrypt(content, aesKey);
        byte[] bytes1 = RSAUtil.aesDecrypt(bytes, aesKey);

        System.out.println(new String(bytes1, "UTF-8"));

    }
}