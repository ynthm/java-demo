package com.ynthm.tools;

import com.ynthm.tools.domain.User;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.temporal.TemporalAdjusters.lastInMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

/**
 * Author : Ynthm
 */
public class StreamTest {

    private User user1;

    public static void main(String[] args) {

        List<User> list = new ArrayList<>();
        User user1 = new User();
        user1.setAge(10);
        user1.setName("abc");
        list.add(user1);

        User user2 = new User();
        user2.setAge(2);
        user2.setName("123");
        list.add(user2);

        list.sort(Comparator.comparingInt(User::getAge).thenComparing(User::getName));
        list.forEach(System.out::println);

        String names = list.stream().map(User::getName).collect(Collectors.joining(";"));
        System.out.println(names);

        User user = list.stream().filter(item -> item.equals("abc")).findFirst().orElse(null);

        System.out.println(user);

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

        BigDecimal a = new BigDecimal(2.9d);
        BigDecimal b = new BigDecimal(7.4d);
        BigDecimal c = new BigDecimal(10.2d);

        System.out.println((a.add(b)).compareTo(c));

        // 返回下一个距离当前时间最近的星期日
        LocalDate date = LocalDate.now();
        LocalDate date7 = date.with(nextOrSame(DayOfWeek.SUNDAY));
        System.out.println(date7);
        // 返回本月最后一个星期六
        LocalDate date9 = date.with(lastInMonth(DayOfWeek.SATURDAY));
        System.out.println(date9);

        Integer abc = Stream.of(1, 4, 2, 5, 6, 3)
                .filter(i -> i > 3)
                .findFirst()
                .orElse(null);
        System.out.println(abc);

    }
}
