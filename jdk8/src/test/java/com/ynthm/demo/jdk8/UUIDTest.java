package com.ynthm.demo.jdk8;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UUIDTest {

  /**
   * 1个UUID是1个16字节（128位）的数字 UUID的格式是这样的：xxxxxxxx-xxxx-Mxxx-Nxxx-xxxxxxxxxxxx
   * 1个UUID被连字符分为五段，形式为8-4-4-4-12的32个字符。 其中的字母是16进制表示，大小写无关。 N那个位置，代表 variant，只会是8,9,a,b M那个位置，代表
   * 版本号，由于UUID的标准实现有5个版本，所以只会是1,2,3,4,5 版本1：基于时间的UUID 版本2：DCE安全的UUID 版本3：基于名字空间的UUID（MD5）
   * 版本4：基于随机数的UUID 版本5：基于名字空间的UUID（SHA1）
   */
  @Test
  void test() {
    UUID uuid = UUID.randomUUID();

    System.out.println(uuid);
    System.out.println(uuid.variant()); // 2
    System.out.println(uuid.version());
  }
}
