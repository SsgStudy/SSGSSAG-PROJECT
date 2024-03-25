package com.ssg.ssgssag.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssg.ssgssag.dto.AnalysisAndSuggestionDTO;
import com.ssg.ssgssag.dto.ChatGptResponseDto;
import com.ssg.ssgssag.dto.QuestionRequestDto;
import com.ssg.ssgssag.service.ChatGptService;
import lombok.extern.log4j.Log4j2;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/chat-gpt")
public class openAIController {
    private final ChatGptService chatGptService;

    public openAIController(ChatGptService chatGptService) {
        this.chatGptService = chatGptService;
    }

    @PostMapping("/question")
    public String sendQuestion(@RequestBody QuestionRequestDto requestDto, Model model) {
        ChatGptResponseDto response = chatGptService.askQuestion(requestDto);
        response.getChoices().forEach(choice -> {
            String contentJson = choice.getMessage().getContent();
            AnalysisAndSuggestionDTO analysisAndSuggestion = parseContentToJson(contentJson);
            model.addAttribute("analysis", analysisAndSuggestion.getAnalysis());
            model.addAttribute("suggestion", analysisAndSuggestion.getSuggestion());
        });
        log.info(model.getAttribute("analysis"));
        log.info(model.getAttribute("suggestion"));
        return "resultPage"; // 결과를 보여줄 페이지 이름
    }


    public AnalysisAndSuggestionDTO parseContentToJson(String contentJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(contentJson, AnalysisAndSuggestionDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new AnalysisAndSuggestionDTO(); // 예외 발생 시 빈 객체 반환
        }
    }
}
