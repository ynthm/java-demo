package com.ynthm.tools.json;

import com.google.gson.Gson;
import com.ynthm.tools.json.bean.ResultResult;
import com.ynthm.tools.util.SecurityUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GsonTest {

    public static void main(String[] args) throws IOException {
        Gson gson = new Gson();
        File jsonFile = new File("src/main/resources/abc.json");
        byte[] bytes = Files.readAllBytes(jsonFile.toPath());
        String json = new String(bytes, StandardCharsets.UTF_8);

        ResultResult resultResult = gson.fromJson(json, ResultResult.class);

        System.out.println(resultResult);

        byte[] encode = Base64.getEncoder().encode("ab123456".getBytes(StandardCharsets.UTF_8));
        String pw = new String(encode);
        System.out.println(pw);
        byte[] decode = Base64.getDecoder().decode(pw);
        System.out.println(new String(decode));
    }
}
