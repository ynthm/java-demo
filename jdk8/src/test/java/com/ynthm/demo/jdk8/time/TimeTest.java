package com.ynthm.demo.jdk8.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public class TimeTest {

  public static void main(String[] args) {

    //    new DateTimeFormatterBuilder().appendFraction(ChronoField.YEAR, 2, 4, true)

    System.out.println(convertStringToDate("14/06/2015"));
    System.out.println(convertStringToDate("4/6/2013"));
    System.out.println(convertStringToDate("14/06/2015"));
    System.out.println(convertStringToDate("4/6/2013"));

    System.out.println(convertStringToDate("2015-6-4"));
    System.out.println(convertStringToDate("2015-06-4"));
    System.out.println(convertStringToDate("2015-6-04"));
    System.out.println(convertStringToDate("2015-06-04"));
  }

  public static final DateTimeFormatter DTF_1 = DateTimeFormatter.ofPattern("d/M/yyyy");
  public static final DateTimeFormatter DTF_2 = DateTimeFormatter.ofPattern("yyyy-M-d");

  public static LocalDateTime convertStringToDate(String dateString) {
    if (dateString == null) {
      return null;
    }
    LocalDate result;
    int indexOfSlash = dateString.indexOf('/');
    if (indexOfSlash <= 2 && indexOfSlash >= 0) {
      result = LocalDate.parse(dateString, DTF_1);
    } else {
      result = LocalDate.parse(dateString, DTF_2);
    }
    return result.atStartOfDay();
  }
}
