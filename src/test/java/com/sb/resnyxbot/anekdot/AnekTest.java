package com.sb.resnyxbot.anekdot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AnekTest {

    @Autowired
    private AnekdotService anekdotService;

    @Test
    void testRandom() {
        Anek anek = anekdotService.random();
        Assertions.assertFalse(anek.getText().isEmpty());
        Assertions.assertFalse(anek.getText().contains("<br>"));
    }

}