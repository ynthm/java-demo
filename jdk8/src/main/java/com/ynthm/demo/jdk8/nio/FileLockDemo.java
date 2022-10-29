package com.ynthm.demo.jdk8.nio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public class FileLockDemo {
  public static void main(String[] args) throws IOException {
    String input = "* end of the file.";
    System.out.println("Input string to the test file is: " + input);
    ByteBuffer buf = ByteBuffer.wrap(input.getBytes());
    String fp = "testout-file.txt";
    Path pt = Paths.get(fp);
    try (FileChannel fc =
        FileChannel.open(pt, StandardOpenOption.WRITE, StandardOpenOption.APPEND)) {
      System.out.println("File channel is open for write and Acquiring lock...");
      // position of a cursor at the end of file
      fc.position(fc.size() - 1);
      FileLock lock = fc.lock();
      System.out.println("The Lock is shared: " + lock.isShared());
      fc.write(buf);
      // Releases the Lock fc.close();
    }
    System.out.println(
        "Content Writing is complete. Therefore close the channel and release the lock.");
    print(fp);
  }

  public static void print(String path) throws IOException {
    FileReader filereader = new FileReader(path);
    BufferedReader bufferedreader = new BufferedReader(filereader);
    String tr = bufferedreader.readLine();
    System.out.println("The Content of testout-file.txt file is: ");
    while (tr != null) {
      System.out.println("    " + tr);
      tr = bufferedreader.readLine();
    }
    filereader.close();
    bufferedreader.close();
  }
}
