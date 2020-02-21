package com.ynthm.jdk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;

import static java.time.temporal.TemporalAdjusters.lastInMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

@DisplayName("JDK8 Time API 测试")
public class TimeTest {

  @Test
  void testDate() {

    LocalDate today = LocalDate.now();
    System.out.println("今天的日期:" + today);
    int year = today.getYear();
    int month = today.getMonthValue();
    int day = today.getDayOfMonth();

    System.out.println("year:" + year);
    System.out.println("month:" + month);
    System.out.println("day:" + day);

    LocalDate birth = LocalDate.of(1986, 1, 1);
    System.out.println("自定义日期:" + birth);

    LocalDate now = LocalDate.now();

    LocalDate date2 = LocalDate.of(2018, 2, 5);

    if (today.equals(now)) {
      System.out.println("时间相等");
    }

    if (!today.equals(date2)) {
      System.out.println("时间不等");
    }

    MonthDay birthday = MonthDay.of(birth.getMonth(), birth.getDayOfMonth());
    MonthDay currentMonthDay = MonthDay.from(today);
    if (currentMonthDay.equals(birthday)) {
      System.out.println("是你的生日");
    } else {
      System.out.println("你的生日还没有到");
    }

    LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);
    System.out.println("一周后的日期为:" + nextWeek);

    LocalDate previousYear = today.minus(1, ChronoUnit.YEARS);
    System.out.println("一年前的日期:" + previousYear);

    LocalDate nextYear = today.plus(1, ChronoUnit.YEARS);
    System.out.println("一年后的日期:" + nextYear);

    LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
    if (tomorrow.isAfter(today)) {
      System.out.println("之后的日期:" + tomorrow);
    }

    LocalDate yesterday = today.minus(1, ChronoUnit.DAYS);
    if (yesterday.isBefore(today)) {
      System.out.println("之前的日期:" + yesterday);
    }

    // 是否是闰年
    if (today.isLeapYear()) {
      System.out.println("This year is Leap year");
    } else {
      System.out.println("2018 is not a Leap year");
    }

    Period period = Period.between(birth, today);
    System.out.println("from 1986-01-01 to today");
    System.out.println("years:" + period.getYears());
    System.out.println("months:" + period.getMonths());
    System.out.println("days" + period.getDays());
    System.out.println("总月数:" + period.toTotalMonths());
    // 计算两个日期之间的天数 Epoch就是从1970-01-01（ISO）开始的天数
    long daysFromBirth = today.toEpochDay() - birth.toEpochDay();
    long daysFromBirth1 = ChronoUnit.DAYS.between(birth, today);
    System.out.println("总天数:" + daysFromBirth);
    Assertions.assertEquals(daysFromBirth, daysFromBirth1);

    LocalDate from = today.withDayOfYear(1);
    System.out.println(from);
    int days = (int) ChronoUnit.DAYS.between(from, today);
    System.out.println(days);
    System.out.println("一年的第几天:" + today.getDayOfYear());
  }

  @Test
  void testModifyLocalDate() {
    LocalDate date = LocalDate.of(2017, 3, 18);
    // with 非静态方法，以该 Temporal 对象为模板，对某些状态进行修改创建该对象的副本
    LocalDate date11 = date.withYear(2011);
    LocalDate date12 = date11.withDayOfMonth(25);
    LocalDate date13 = date12.with(ChronoField.MONTH_OF_YEAR, 9);
    Assertions.assertEquals(date13, LocalDate.of(2011, 9, 25));

    // 非静态方法，创建 Temporal 对象的一个副本，通过将当前 Temporal 对象的值加上或减去一定的时长创建该副本
    LocalDate date21 = date.plusWeeks(1);
    LocalDate date22 = date21.minusYears(3);
    LocalDate date23 = date22.plus(6, ChronoUnit.MONTHS);
    Assertions.assertEquals(date23, LocalDate.of(2014, 9, 25));
  }

  static TemporalAdjuster NEXT_WORKING_DAY =
      TemporalAdjusters.ofDateAdjuster(
          date -> {
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            int daysToAdd;
            if (dayOfWeek == DayOfWeek.FRIDAY) daysToAdd = 3;
            else if (dayOfWeek == DayOfWeek.SATURDAY) daysToAdd = 2;
            else daysToAdd = 1;
            return date.plusDays(daysToAdd);
          });

  /**
   * TemporalAdjuster 类是调整Temporal对象的策略 TemporalAdjusters 类中预定义实现
   * TemporalAdjusters类有很多预定义的static方法返回TemporalAdjuster对象，使用不同方式调节Temporal对象而与Temporal实现无关。
   */
  @Test
  void testTemporalAdjuster() {
    LocalDate today = LocalDate.now();
    // 返回下一个距离当前时间最近的星期日
    LocalDate date7 = today.with(nextOrSame(DayOfWeek.SUNDAY));
    System.out.println(date7);
    // 返回本月最后一个星期六
    LocalDate date9 = today.with(lastInMonth(DayOfWeek.SATURDAY));
    System.out.println(date9);

    // 使用DateAdjuster将今天的日期调整到下周一
    LocalDate nextMonday = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
    System.out.println("下周一: " + nextMonday);
    // 使用 TemporalAdjuster 将今天的日期调整为月的最后一天
    LocalDate lastDayOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());
    System.out.println("这个月最后一天: " + lastDayOfMonth);

    LocalDate localDate = LocalDate.of(2017, 07, 8);
    LocalDate nextSunday = localDate.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
    String expected = "2017-07-09";
    Assertions.assertEquals(expected, nextSunday.toString());

    // 自定义 TemporalAdjuster
    TemporalAdjuster temporalAdjuster = t -> t.plus(Period.ofDays(14));
    LocalDate result = localDate.with(temporalAdjuster);
    String fourteenDaysAfterDate = "2017-07-22";
    Assertions.assertEquals(fourteenDaysAfterDate, result.toString());

    // 创建一个在3个月2天后日期的 TemporalAdjuster
    TemporalAdjuster adjuster =
        TemporalAdjusters.ofDateAdjuster((LocalDate date) -> date.plusMonths(3).plusDays(2));
    LocalDate dayAfter3Mon2Day = today.with(adjuster);
    System.out.println("三月零两天后的日期: " + dayAfter3Mon2Day);

    LocalDate nextWorkDay = localDate.with(NEXT_WORKING_DAY);
    Assertions.assertEquals("2017-07-10", nextWorkDay.toString());
  }

  @Test
  void testTime() {
    LocalTime time = LocalTime.now();
    System.out.println("获取当前的时间,不含有日期:" + time);
    LocalTime newTime = time.plusHours(3);
    System.out.println("三个小时后的时间为:" + newTime);
  }

  @Test
  void testTimestamp() {
    Instant instant = Instant.now();
    System.out.println("时间戳:" + instant.toEpochMilli());

    System.out.println("时间戳:" + System.currentTimeMillis());
    System.out.println(instant.atZone(ZoneId.of("Asia/Shanghai")).toInstant().toEpochMilli());
    System.out.println(instant.atZone(ZoneId.of("GMT+08:00")).toInstant().toEpochMilli());
    System.out.println(instant.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
  }

  @Test
  void testClock() {
    // Returns the current time based on your system clock and set to UTC.
    Clock clock = Clock.systemUTC();
    System.out.println("Clock : " + clock.millis());

    // Returns time based on system clock zone
    Clock defaultClock = Clock.systemDefaultZone();
    System.out.println("Clock : " + defaultClock.millis());
  }

  @Test
  void testZone() {
    ZoneId zoneId = ZoneId.of("UTC+8");
    LocalTime lt = LocalTime.now(zoneId);
    System.out.println(lt);

    ZoneId zoneId1 = ZoneId.of("Asia/Shanghai");
    LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(zoneId1), LocalTime.MIN);
    ZonedDateTime zdt = todayStart.atZone(zoneId1);
    String am0 = zdt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    System.out.println(am0);

    ZonedDateTime zdt1 = zdt.plusHours(8).withZoneSameLocal(ZoneOffset.UTC);
    String am1 = zdt1.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    System.out.println(am1);

    LocalDate ld = LocalDate.now(zoneId);
    LocalDateTime todayStart1 = LocalDateTime.of(ld, LocalTime.MIN);
    ZonedDateTime todayStart2 = todayStart1.minusHours(8).atZone(ZoneOffset.UTC);
    String am2 = todayStart2.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    System.out.println(am2);

    ZoneId america = ZoneId.of("America/New_York");
    LocalDateTime localDateAndTime = LocalDateTime.now();
    System.out.println("当前时间:" + localDateAndTime);
    ZonedDateTime dateAndTimeInNewYork = ZonedDateTime.of(localDateAndTime, america);
    System.out.println("Current date and time in a particular timezone : " + dateAndTimeInNewYork);
  }

  @Test
  void testYearMonth() {
    YearMonth currentYearMonth = YearMonth.now();
    System.out.printf("year month: %s%n", currentYearMonth);

    int days = YearMonth.of(2020, 2).lengthOfMonth();
    System.out.println("2020年2月有多少天:" + days);

    YearMonth creditCardExpiry = YearMonth.of(2019, Month.FEBRUARY);
    System.out.printf("Your credit card expires on %s %n", creditCardExpiry);
  }

  @Test
  void testFormat() {

    String dayStr = "20180205";
    LocalDate formatted = LocalDate.parse(dayStr, DateTimeFormatter.BASIC_ISO_DATE);
    System.out.println(dayStr + "  格式化后的日期为:  " + formatted);

    LocalDateTime date = LocalDateTime.now();

    DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    // 日期转字符串
    String str = date.format(format1);

    System.out.println("日期转换为字符串:" + str);

    DateTimeFormatter format2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    // 字符串转日期
    LocalDate date2 = LocalDate.parse(str, format2);
    System.out.println("日期类型:" + date2);
  }

  /**
   * Duration类主要用于以秒和纳 秒衡量时间的长短。
   *
   * <p>不能向between方法传递一个LocalDate对象做参数，否则会抛异常 UnsupportedTemporalTypeException
   *
   * <p>如果你需要以年、月或者日的方式对多个时间单位建模，可以使用Period类。使用该类的 工厂方法between，你可以使用得到两个LocalDate之间的时长
   */
  @Test
  void testDuration() {

    LocalTime time1 = LocalTime.of(15, 7, 50);
    LocalTime time2 = LocalTime.of(16, 8, 50);
    LocalDateTime dateTime1 = LocalDateTime.of(2017, Month.MARCH, 12, 14, 22, 28);
    LocalDateTime dateTime2 = LocalDateTime.of(2018, Month.MARCH, 12, 14, 22, 28);
    Instant instant1 = Instant.ofEpochSecond(1);
    Instant instant2 = Instant.now();
    Duration d1 = Duration.between(time1, time2);
    Duration d2 = Duration.between(dateTime1, dateTime2);
    Duration d3 = Duration.between(instant1, instant2);
    // 这里会抛异常java.time.temporal.UnsupportedTemporalTypeException: Unsupported unit: Seconds
    Assertions.assertThrows(
        UnsupportedTemporalTypeException.class,
        () -> {
          LocalDate date1 = LocalDate.of(2017, 12, 12);
          LocalDate date2 = LocalDate.of(2017, 12, 24);
          Duration d4 = Duration.between(date1, date2);
        });

    System.out.println(
        "TimeTest.testDuration d1: "
            + d1.getSeconds()
            + "\td2: "
            + d2.getSeconds()
            + "\td3: "
            + d3.getSeconds());
  }
}
