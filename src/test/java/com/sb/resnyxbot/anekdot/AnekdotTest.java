package com.sb.resnyxbot.anekdot;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class AnekdotTest {

    @Test
    void testRandom() {
        Anekdot anekdot = new Anekdot(null);
        Anek anek = anekdot.random();
        LOG.info("{} = {}", anek.getId(), anek.getText());
        Assertions.assertFalse(anek.getText().isEmpty());
    }

}