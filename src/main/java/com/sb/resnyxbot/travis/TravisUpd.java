package com.sb.resnyxbot.travis;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public final class TravisUpd {

    private String type;

    private String state;

    private Integer status;

    private Integer result;

    @JsonProperty("status_message")
    private String statusMsg;

    @JsonProperty("result_message")
    private String resultMsg;

    private Repository repository;

    public String msg() {
        String emo = result == 0 ?
                "\uD83E\uDD73" :
                "❌";
        return String.format(
                "_Travis build_%n*%s* : %s",
                repository.getName(),
                emo
        );
    }
}
