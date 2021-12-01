package com.example.testproject.quiz_result.dto;

import com.example.testproject.result_answer.dto.ResultAnswerDto;
import lombok.Data;

import java.util.Set;

@Data
public class QuizResultDto {

    private Long testId;
    Set<ResultAnswerDto> userAnswers;
}
