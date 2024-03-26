package com.ssg.ssgssag.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatGptRequestDto implements Serializable {
    private String model;
    private List<Message> messages; // messages 리스트 추가
    @JsonProperty("max_tokens")
    private Integer maxTokens;
    private Double temperature;
    @JsonProperty("top_p")
    private Double topP;

    // 생성자 및 빌더 패턴 수정
    @Builder
    public ChatGptRequestDto(String model, List<Message> messages,
        Integer maxTokens, Double temperature,
        Double topP) {
        this.model = model;
        this.messages = messages; // 생성자 수정
        this.maxTokens = maxTokens;
        this.temperature = temperature;
        this.topP = topP;
    }
}
