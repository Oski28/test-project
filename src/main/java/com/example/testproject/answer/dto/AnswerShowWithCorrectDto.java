package com.example.testproject.answer.dto;

import lombok.Data;

@Data
public class AnswerShowWithCorrectDto {

    private Long id;
    private String text;
    private Boolean correct;
}
