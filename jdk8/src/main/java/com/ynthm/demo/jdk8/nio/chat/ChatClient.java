package com.ynthm.demo.jdk8.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public class ChatClient {

  private final String name;

  public ChatClient(String name) {
    this.name = name;
  }

  public void start() throws IOException {

    try (Selector selector = Selector.open()) {
      try (SocketChannel socketChannel =
          SocketChannel.open(new InetSocketAddress("localhost", 8000))) {
        socketChannel.configureBlocking(false);

        socketChannel.register(selector, SelectionKey.OP_READ);
        new Thread(new ClientHandler(selector)).start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
          String msg = scanner.nextLine();
          if (msg.length() > 0) {
            socketChannel.write(ByteBuffer.wrap((name + ":" + msg).getBytes()));
          }
        }
      }
    }
  }
}
