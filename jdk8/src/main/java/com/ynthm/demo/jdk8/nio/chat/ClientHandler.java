package com.ynthm.demo.jdk8.nio.chat;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public class ClientHandler implements Runnable {

  private final Selector selector;

  public ClientHandler(Selector selector) {
    this.selector = selector;
  }

  @Override
  public void run() {
    try {
      for (; ; ) {
        int readyChannels = selector.select();
        if (readyChannels == 0) {
          continue;
        }

        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()) {
          SelectionKey selectionKey = iterator.next();
          iterator.remove();
          readHandler(selector, selectionKey);
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void readHandler(Selector selector, SelectionKey selectionKey) throws IOException {
    if (selectionKey.isReadable()) {
      ByteBuffer buffer = ByteBuffer.allocate(1024);
      SocketChannel client = (SocketChannel) selectionKey.channel();

      StringBuilder response = new StringBuilder();
      while (client.read(buffer) > 0) {
        buffer.flip();
        response.append(StandardCharsets.UTF_8.decode(buffer).toString());
      }
      // 将channel再次注册到selector上，监听他的可读事件
      client.register(selector, SelectionKey.OP_READ);
      // 将服务器端响应信息打印到本地
      if (response.length() > 0) {
        System.out.println(response);
      }
    }
  }
}
