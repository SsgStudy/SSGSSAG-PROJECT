package com.ssg.ssgssag.controller;

import com.ssg.ssgssag.dto.ChatGptResponseDto;
import com.ssg.ssgssag.dto.QuestionRequestDto;
import com.ssg.ssgssag.service.ChatGptService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat-gpt")
public class openAIController {
    private final ChatGptService chatGptService;

    public openAIController(ChatGptService chatGptService) {
        this.chatGptService = chatGptService;
    }

    @PostMapping("/question")
    public ChatGptResponseDto sendQuestion(@RequestBody QuestionRequestDto requestDto) {
        return chatGptService.askQuestion(requestDto);
    }
}
