package com.ynthm;

import com.ynthm.tools.domain.User;
import org.junit.jupiter.api.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

public class StandardTests {
  @BeforeAll
  static void initAll() {
    System.out.println("init all");
  }

  @BeforeEach
  void init() {
    System.out.println("init");
  }

  @AfterEach
  void teardown() {
    System.out.println("teardown");
  }

  @AfterAll
  static void teardownAll() {
    System.out.println("teardown all");
  }

  @Test
  void standardAssertions() {
    assertEquals(2, 2);
    assertEquals(2, 2, "yes you are right.");
    assertTrue(2 == 2);
  }

  @Test
  void groupedAssertions() {
    User user = new User();
    user.setName("Ethan");
    user.setAge(18);
    assertAll(
        "user", () -> assertEquals("Ethan", user.getName()), () -> assertEquals(18, user.getAge()));
  }

  @Tag("DEV")
  @Test
  void exceptionTest() {
    Throwable exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              throw new IllegalArgumentException("a message");
            });

    assertEquals("a message", exception.getMessage());
  }

  @Test
  void timeoutNotExceeded() {
    assertTimeout(Duration.ofMinutes(2), () -> {});

    String greeting =
        assertTimeout(
            Duration.ofMillis(2),
            () -> {
              return "hello";
            });

    assertEquals("hello", greeting);

    // 静态方法
    greeting = assertTimeout(Duration.ofMinutes(2), StandardTests::greeting);

    assertEquals("hello", greeting);
  }

  @Test
  void assumingTest() {
    System.setProperty("env", "DEV");
    assumingThat(
        "DEV".equals(System.getenv("env")),
        () -> {
          // 假设成立才执行
          assertEquals(1, 1);
        });
  }

  private static String greeting() {
    return "hello";
  }
}
