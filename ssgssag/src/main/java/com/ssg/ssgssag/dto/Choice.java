package com.ssg.ssgssag.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Choice implements Serializable {

    private String text;
    private Integer index;
    @JsonProperty("finish_reason")
    private String finishReason;
    private Message message;

    @Builder
    public Choice(String text, Integer index, String finishReason, Message message) {
        this.text = text;
        this.index = index;
        this.finishReason = finishReason;
        this.message = message;
    }
}
