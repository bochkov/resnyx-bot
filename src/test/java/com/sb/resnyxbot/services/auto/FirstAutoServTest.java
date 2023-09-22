package com.sb.resnyxbot.services.auto;

import java.util.List;

import com.sb.resnyxbot.services.auto.model.Region;
import com.sb.resnyxbot.services.auto.service.AutoService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import resnyx.TgMethod;
import resnyx.messenger.general.Chat;
import resnyx.messenger.general.Message;
import resnyx.messenger.general.SendMessage;

@SpringBootTest
@Sql(scripts = {"classpath:schema0.sql", "classpath:data.sql"})
class FirstAutoServTest {

    @Autowired
    private AutoService autoService;
    @Autowired
    private AutoResnyx autoResnyx;

    @Test
    @DisplayName("Test fetch data from table")
    void testFindRegionByName() {
        List<Region> regions = autoService.findRegionByName("Свердловск");
        Assertions.assertThat(regions).hasSize(1);
        Assertions.assertThat(regions.get(0).getCodes()).hasSize(3);
    }

    @Test
    @DisplayName("Testing AutoService Tg Answer")
    void testAnswer() {
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(123L);
        message.setChat(chat);
        message.setText("/авто москва");

        List<TgMethod> res = autoResnyx.answer(message);
        Assertions.assertThat(res).hasSize(1);
        TgMethod method = res.get(0);
        Assertions.assertThat(method).isInstanceOf(SendMessage.class);
        SendMessage send = (SendMessage) method;
        Assertions.assertThat(send.getText()).isEqualTo("Москва = 77, 97, 99, 177, 197, 199, 777, 797");
    }

    @Test
    @DisplayName("Testing AutoService Tg Answer")
    void testAnswerPart() {
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(123L);
        message.setChat(chat);
        message.setText("/авто алтай");

        List<TgMethod> res = autoResnyx.answer(message);
        Assertions.assertThat(res).hasSize(2);
        for (TgMethod method : res) {
            Assertions.assertThat(method).isInstanceOf(SendMessage.class);
            SendMessage send = (SendMessage) method;
            Assertions.assertThat(send.getText()).isNotEmpty();
        }
    }
}