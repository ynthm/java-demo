package com.ynthm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import java.util.Map;
import java.util.Properties;

public class DemoTest {

    @Test
    @EnabledOnOs(OS.MAC)
    void testOnMac() {
        System.out.println("test on macOS");
    }

    @Test
    @EnabledOnOs({OS.LINUX, OS.WINDOWS})
    void onLinuxOrWindows() {
        System.out.println("Run this test on Windows or Linux.");
    }

    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void onJava9() {
        System.out.println("Run this on Java 8");
    }

    @Test
    void printSystemProperties() {
        Properties properties = System.getProperties();
        System.out.println(properties.get("user.country"));
        // properties.forEach((k, v) -> System.out.println(k + ": " + v));
    }

    @Test
    @DisabledIfSystemProperty(named = "user.country", matches = "CN")
    void notOnCountry() {
        System.out.println("Do not run this on country code CN");
    }

    @Test
    void printEnvironmentProperties() {
        Map<String, String> env = System.getenv();
        System.out.println(env.get("USER"));
        // env.forEach((k, v) -> System.out.println(k + ": " + v));
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "USER", matches = "ethan")
    void onUser() {
        System.out.println("Run this if user is ethan.");
    }
}
