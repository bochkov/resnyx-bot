package com.sb.resnyxbot.prop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = Prop.COLLECTION_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class Prop {

    public static final String COLLECTION_NAME = "resnyx";

    @Id
    private String id;
    private String value;
}
