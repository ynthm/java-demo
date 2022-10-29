package com.ynthm.demo.jdk8.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public class PipeDemo {
  public static void main(String[] args) throws IOException {
    // The Pipe is created
    Pipe pipe = Pipe.open();
    // For accessing the pipe sink channel
    ByteBuffer bb;
    try (Pipe.SinkChannel skChannel = pipe.sink()) {
      String td = "Data is successfully sent for checking the java NIO Channel Pipe.";
      bb = ByteBuffer.allocate(1024);
      bb.clear();
      bb.put(td.getBytes());
      bb.flip();
      // write the data into a sink channel.
      while (bb.hasRemaining()) {
        skChannel.write(bb);
      }
    }

    // For accessing the pipe source channel
    try (Pipe.SourceChannel sourceChannel = pipe.source()) {
      bb = ByteBuffer.allocate(512);
      // The data is writing to the console
      while (sourceChannel.read(bb) > 0) {
        bb.flip();

        while (bb.hasRemaining()) {
          char testData = (char) bb.get();
          System.out.print(testData);
        }
        bb.clear();
      }
    }
  }
}
