package com.ynthm.tools.util;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class StringUtilsTest {
    @Test
    void test(){
        String[] split1 = StringUtils.split("1/2/", '/');
        String[] split = "1/2/".split("/");
        System.out.println(StringUtils.substring("1-2-3-4", 2, 3));
    }

}
