package com.ynthm.demo.jdk8.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public class ChatServer {

  public static void main(String[] args) throws IOException {
    try (Selector selector = Selector.open()) {
      try (ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
        serverSocket.bind(new InetSocketAddress("localhost", 8000));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        ByteBuffer buffer = ByteBuffer.allocate(256);

        for (; ; ) {
          int flag = selector.select();
          if (flag > 0) {
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            while (iter.hasNext()) {

              SelectionKey key = iter.next();
              // 一定要把当前key删掉，防止重复处理
              iter.remove();

              if (key.isAcceptable()) {
                register(selector, serverSocket);
              }

              if (key.isReadable()) {
                broadcasting(buffer, selector, key);
              }
            }
          }
        }
      }
    }
  }

  private static void broadcasting(ByteBuffer buffer, Selector selector, SelectionKey key)
      throws IOException {

    SocketChannel client = (SocketChannel) key.channel();
    int readLength = client.read(buffer);
    if (readLength > 0) {
      buffer.flip();
      castOtherClient(buffer, selector, client);
      buffer.clear();
    }
  }

  private static void castOtherClient(
      ByteBuffer buffer, Selector selector, SocketChannel socketChannel) throws IOException {
    Set<SelectionKey> selectionKeySet = selector.keys();
    for (SelectionKey selectionKey : selectionKeySet) {
      SelectableChannel targetChannel = selectionKey.channel();
      if (targetChannel instanceof SocketChannel && targetChannel != socketChannel) {
        ((SocketChannel) targetChannel).write(buffer);
      }
    }
  }

  private static void register(Selector selector, ServerSocketChannel serverSocket)
      throws IOException {

    SocketChannel client = serverSocket.accept();
    client.configureBlocking(false);
    client.register(selector, SelectionKey.OP_READ);
    // 回复客户端提示信息
    client.write(StandardCharsets.UTF_8.encode("<==== 欢迎进入聊天室 ====>"));
  }
}
