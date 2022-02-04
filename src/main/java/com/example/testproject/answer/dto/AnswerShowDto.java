package com.example.testproject.answer.dto;

import lombok.Data;

@Data
public class AnswerShowDto {

    private Long id;
    private String text;
    private Boolean correct;
}
