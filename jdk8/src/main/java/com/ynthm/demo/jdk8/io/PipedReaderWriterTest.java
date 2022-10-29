package com.ynthm.demo.jdk8.io;

import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 线程间通讯，管道流
 */
public class PipedReaderWriterTest {
    public static void main(String[] args) {
        try {
            // Create writer and reader instances
            PipedReader pr = new PipedReader();
            PipedWriter pw = new PipedWriter();

            // Connect the writer with reader
            pw.connect(pr);

            // Create one writer thread and one reader thread
            Thread thread1 = new Thread(new PipeReaderThread("ReaderThread", pr));

            Thread thread2 = new Thread(new PipeWriterThread("WriterThread", pw));

            // start both threads
            thread1.start();
            thread2.start();

        } catch (Exception e) {
            System.out.println("PipeThread Exception: " + e);
        }
    }
}
