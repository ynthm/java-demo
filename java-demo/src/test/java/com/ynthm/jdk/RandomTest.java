package com.ynthm.jdk;

import com.google.common.cache.RemovalListener;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class RandomTest {

    @Test
    void random(){
        Random random = new Random();
        IntConsumer intConsumer = System.out::println;
        random.ints(3).forEach(intConsumer);
        System.out.println();
        random.ints(3,1,10).forEach(intConsumer);

        random.doubles().limit(10).forEach(System.out::println);
    }

    /**
     * How to generate secure random number
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     */
    @Test
    void secureRandom() throws NoSuchProviderException, NoSuchAlgorithmException {
        SecureRandom secureRandomGenerator = SecureRandom.getInstance("SHA1PRNG", "SUN");

        // Get 128 random bytes
        byte[] randomBytes = new byte[128];
        secureRandomGenerator.nextBytes(randomBytes);
        System.out.println(randomBytes);

        //Get random integer
        int r = secureRandomGenerator.nextInt();
        System.out.println(r);

        //Get random integer in range
        int randInRange = secureRandomGenerator.nextInt(999999);
        System.out.println(randInRange);
    }
}
