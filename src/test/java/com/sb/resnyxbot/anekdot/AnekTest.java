package com.sb.resnyxbot.anekdot;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class AnekTest {

    @Autowired
    private AnekdotService anekdotService;

    @Test
    void testRandom() {
        Anek anek = anekdotService.random();
        LOG.info("{} = {}", anek.getId(), anek.getText());
        Assertions.assertFalse(anek.getText().isEmpty());
        Assertions.assertFalse(anek.getText().contains("<br>"));
        LOG.info("{}", anek.getText());
    }

}