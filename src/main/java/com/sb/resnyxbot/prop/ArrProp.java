package com.sb.resnyxbot.prop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = Prop.COLLECTION_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class ArrProp {

    @Id
    private String id;
    private ChatId[] value;

    @Data
    public final class ChatId {

        @Field("chat_id")
        private Long id;

    }
}
