package com.sb.resnyxbot.services.anekdot;

import com.sb.resnyxbot.services.anekdot.model.Anek;
import com.sb.resnyxbot.services.anekdot.service.AnekdotService;
import org.assertj.core.api.Assertions;
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
        Assertions.assertThat(anek.getText())
                .isNotEmpty()
                .contains("<br>");
    }

}