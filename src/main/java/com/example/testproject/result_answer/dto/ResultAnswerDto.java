package com.example.testproject.result_answer.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ResultAnswerDto {

    @NotBlank(message = "Text cannot be blank.")
    @Size(min = 1, max = 300, message = "Text must contain between 1 and 300 characters.")
    private String text;

    private Long questionId;
    private Long answerId; // dla pytań zamkniętych
}
