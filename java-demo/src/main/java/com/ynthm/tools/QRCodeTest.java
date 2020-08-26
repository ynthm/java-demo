package com.ynthm.tools;

import com.ynthm.tools.util.QRCodeUtil;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Author : Ynthm
 */
public class QRCodeTest {


    public static void main(String[] args) throws UnsupportedEncodingException {
        String imgPath = "src/main/resources/bg.png";
        String bgImgPath = URLDecoder.decode(imgPath, "utf-8");
        File bgImgFile = new File(bgImgPath);//背景图片
        File QrCodeFile = new File("src/main/resources/myqrcode.png");//生成图片位置
        String url = "https://www.ynthm.com";//二维码链接
        String note = "111";//文字描述
        String tui = "222";//文字描述

        System.out.println("Hello");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {

        }


        InputStream inputStream = QRCodeTest.class.getClassLoader().getResourceAsStream("bg.png");

        //宣传二维码生成
        //生成图地址,背景图地址,二维码宽度,二维码高度,二维码识别地址,文字描述1,文字描述2,文字大小,图片x轴方向,图片y轴方向,文字1||2xy轴方向
        QRCodeUtil.creatQRCode(QrCodeFile, bgImgFile, 148, 148, url, note, tui, 38, 408, 123, 0, 0, 410, 210);

    }
}
