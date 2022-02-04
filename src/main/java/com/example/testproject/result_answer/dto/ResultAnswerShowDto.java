package com.example.testproject.result_answer.dto;

import com.example.testproject.answer.dto.AnswerShowDto;
import lombok.Data;

import java.util.Set;

@Data
public class ResultAnswerShowDto {

    private String questionText;
    private Integer questionPoints;
    private String questionType;
    private String descriptiveAnswerText;
    private Integer answerRatedPoints;
    private Set<AnswerShowDto> correctAnswers;
    private Set<AnswerShowDto> userAnswers;
}
