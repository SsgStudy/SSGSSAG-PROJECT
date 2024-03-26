package com.ssg.ssgssag.dto;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {
    private String role;
    private String content;
}
