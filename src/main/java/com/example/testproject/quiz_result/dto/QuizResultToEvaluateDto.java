package com.example.testproject.quiz_result.dto;

import com.example.testproject.result_answer.dto.ResultAnswerToEvaluateDto;
import lombok.Data;

import java.util.Set;

@Data
public class QuizResultToEvaluateDto {

    private Long studentId;
    private Set<ResultAnswerToEvaluateDto> answers;
}
