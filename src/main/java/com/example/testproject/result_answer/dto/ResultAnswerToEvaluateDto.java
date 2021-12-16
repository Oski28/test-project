package com.example.testproject.result_answer.dto;

import lombok.Data;

@Data
public class ResultAnswerToEvaluateDto {

    private Long id;
    private Long questionId;
    private Long[] selectedClosedAnswersIds;
    private String writtenOpenedAnswers;
}
