package com.sb.resnyxbot.auto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import resnyx.TgMethod;
import resnyx.methods.message.SendMessage;
import resnyx.model.Chat;
import resnyx.model.Message;

import java.util.List;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
public class FirstAutoServTest {

    @Autowired
    private AutoServ autoServ;

    @Test
    @DisplayName("Test fetch data from table")
    void testFindRegionByName() {
        List<Region> regions = autoServ.findRegionByName("Свердловск");
        Assertions.assertEquals(1, regions.size());
        Assertions.assertEquals(3, regions.get(0).getCodes().size());
    }

    @Test
    @DisplayName("Testing AutoService Tg Answer")
    void testAnswer() {
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(123L);
        message.setChat(chat);
        message.setText("/авто москва");

        List<TgMethod<Message>> res = autoServ.answer("token", message);
        Assertions.assertEquals(1, res.size());
        TgMethod<?> method = res.get(0);
        Assertions.assertTrue(method instanceof SendMessage);
        SendMessage send = (SendMessage) method;
        Assertions.assertEquals(
                "Москва = 77, 97, 99, 177, 197, 199, 777, 797",
                send.getText()
        );
    }

    @Test
    @DisplayName("Testing AutoService Tg Answer")
    void testAnswerPart() {
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(123L);
        message.setChat(chat);
        message.setText("/авто алтай");

        List<TgMethod<Message>> res = autoServ.answer("token", message);
        Assertions.assertEquals(2, res.size());
        for (TgMethod<Message> method : res) {
            Assertions.assertTrue(method instanceof SendMessage);
            SendMessage send = (SendMessage) method;
            Assertions.assertFalse(
                    send.getText().isEmpty()
            );
        }
    }
}