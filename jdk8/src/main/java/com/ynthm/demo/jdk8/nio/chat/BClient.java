package com.ynthm.demo.jdk8.nio.chat;

import java.io.IOException;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public class BClient {
  public static void main(String[] args) throws IOException {
    new ChatClient("bob").start();
  }
}
