package com.sb.resnyxbot.anekdot;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class AnekTest {

    @Test
    void testRandom() {
        Anek anek = Anek.random();
        LOG.info("{} = {}", anek.getId(), anek.getText());
        Assertions.assertFalse(anek.getText().isEmpty());
        Assertions.assertFalse(anek.text().contains("<br>"));
    }

}