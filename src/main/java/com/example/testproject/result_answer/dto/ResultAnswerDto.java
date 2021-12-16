package com.example.testproject.result_answer.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ResultAnswerDto {

    @Size(min = 1, max = 300, message = "Text must contain between 1 and 300 characters.")
    private String text;

    @NotNull
    private Long questionId;
    private Long answerId;
    private Long[] answerIds;
}
