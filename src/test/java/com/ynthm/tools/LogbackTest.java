package com.ynthm.tools;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackTest {
    @Test
    void test() {
        Logger logger = LoggerFactory.getLogger(LogbackTest.class);
        logger.info("hello {}", "world");
    }


}
