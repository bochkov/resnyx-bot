package com.sb.resnyxbot.anekdot;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class AnekdotTest {

    @Test
    public void test() {
        String str = "Sun, 25 Oct 2020 00:00:00 +0300";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE, dd LLL yyyy HH:mm:ss Z", Locale.US);
        LOG.info("{}", ZonedDateTime.now().format(dtf));
        LOG.info("{}", ZonedDateTime.parse(str, dtf));
    }

}