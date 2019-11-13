package com.ynthm.tools;

import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.logging.*;

public class JDKLogTest {

    @Test
    void test() throws IOException {
        // 默认 有一个 ConsoleHandler
        Logger logger = Logger.getLogger(JDKLogTest.class.getName());


        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.WARNING);
        consoleHandler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                return record.getLevel() + ":" + record.getMessage() + "\n";
            }
        });
        logger.addHandler(consoleHandler);

        String filePath = "src/test/resources/test.log";
        FileHandler fileHandler = new FileHandler(filePath);
        fileHandler.setLevel(Level.INFO);
        logger.addHandler(fileHandler);

        logger.info("INFO Message");
        logger.warning("WARN Message");
        logger.log(Level.FINE, "SEVERE Message");

    }
}
