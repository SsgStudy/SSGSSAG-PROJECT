package com.ssg.ssgssag.dto;


import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatGptResponseDto implements Serializable {

    private String id;
    private String object;
    private String created;
    private String model;
    private List<Choice> choices;
    private String message;

    @Builder
    public ChatGptResponseDto(String id, String object, String created, String model, List<Choice> choices, String message) {
        this.id = id;
        this.object = object;
        this.created = created;
        this.model = model;
        this.choices = choices;
        this.message = message;
    }
}
