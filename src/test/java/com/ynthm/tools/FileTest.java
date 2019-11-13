package com.ynthm.tools;


import org.junit.jupiter.api.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FileTest {

    @Order(1)
    @Test
    void create() throws IOException {
        Path dir = Paths.get("/Users/ethan/hello");
        if (!Files.exists(dir)) {
            Files.createDirectory(dir);
        }
        Path path = Paths.get("/Users/ethan/hello/hello.txt");
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
    }

    @Order(2)
    @RepeatedTest(2)
    void write() throws IOException {
        Path path = Paths.get("/Users/ethan/hello/hello.txt");
        BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND);
        String str = "hello world!/r/n";

        bufferedWriter.write(str);
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    @Order(3)
    @Test
    void copyFile() throws IOException {
        Path path = Paths.get("/Users/ethan/hello/hello.txt");
        Path path2 = Paths.get("/Users/ethan/hello/world.txt");
        Files.copy(path, path2);
    }

    @Test
    void traverseDirectory() throws IOException {
        Path dir = Paths.get("/Users/ethan/hello/");
        Stream<Path> list = Files.list(dir);
        list.forEach(item -> {
            System.out.println(item.getFileName());
        });


    }

    @Test
    void fileProperties() throws IOException {
        Path path = Paths.get("/Users/ethan/hello/hello.txt");
        if (Files.exists(path)) {
            System.out.println(Files.getLastModifiedTime(path));
            System.out.println(Files.getPosixFilePermissions(path));
            System.out.println(Files.size(path));
        }

    }

    @Test
    void getResources() throws IOException {
        // 必须带/
        InputStream in = this.getClass().getResourceAsStream("/test.properties");
        Properties properties = new Properties();
        properties.load(in);
        System.out.println(properties.get("abc"));
        System.out.println(properties.get("cde"));

    }

    @Test
    void delete() throws IOException {
        Path path = Paths.get("/Users/ethan/hello/hello.txt");
        Files.deleteIfExists(path);

        // 只有空文件夹才能删除
        Path dir = Paths.get("/Users/ethan/hello");
        Files.deleteIfExists(dir);
    }

}
