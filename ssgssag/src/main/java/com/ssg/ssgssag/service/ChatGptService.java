package com.ssg.ssgssag.service;

import com.ssg.ssgssag.config.ChatGptConfig;
import com.ssg.ssgssag.dto.ChatGptRequestDto;
import com.ssg.ssgssag.dto.ChatGptResponseDto;
import com.ssg.ssgssag.dto.Message;
import com.ssg.ssgssag.dto.QuestionRequestDto;
import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatGptService {
    private static RestTemplate restTemplate = new RestTemplate();

    public HttpEntity<ChatGptRequestDto> buildHttpEntity(ChatGptRequestDto requestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ChatGptConfig.MEDIA_TYPE));
        headers.add(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + ChatGptConfig.API_KEY);
        return new HttpEntity<>(requestDto, headers);
    }

    public ChatGptResponseDto getResponse(HttpEntity<ChatGptRequestDto> chatGptRequestDtoHttpEntity) {
        ResponseEntity<ChatGptResponseDto> responseEntity = restTemplate.postForEntity(
            ChatGptConfig.URL,
            chatGptRequestDtoHttpEntity,
            ChatGptResponseDto.class);

        return responseEntity.getBody();
    }

    public ChatGptResponseDto askQuestion(QuestionRequestDto requestDto) {
        // Message 객체 리스트 생성
        List<Message> messages = Collections.singletonList(new Message("user", requestDto.getQuestion()));

        return this.getResponse(
            this.buildHttpEntity(
                new ChatGptRequestDto(
                    ChatGptConfig.MODEL,
                    messages,
                    ChatGptConfig.MAX_TOKEN,
                    ChatGptConfig.TEMPERATURE,
                    ChatGptConfig.TOP_P
                )
            )
        );
    }
}
