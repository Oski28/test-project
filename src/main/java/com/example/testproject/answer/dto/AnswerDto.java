package com.example.testproject.answer.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AnswerDto {

    @NotBlank(message = "Text cannot be blank.")
    @Size(min = 1, max = 300, message = "Text must contain between 1 and 300 characters.")
    private String text;

    private Boolean correct;
}
