package com.ynthm.demo.jdk8.time;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author : Ethan Wang
 */
public class DefaultTimeZoneTest {
  public static void main(String[] args) {
    System.out.println(TimeZone.getDefault().getDisplayName());
    SimpleDateFormat dfOld = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    System.out.println(dfOld.format(new Date()));

    // final TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
    final TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");
    TimeZone.setDefault(timeZone);
    System.out.println(TimeZone.getDefault());
    SimpleDateFormat dfNew = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    System.out.println(dfNew.format(new Date()));
  }
}
