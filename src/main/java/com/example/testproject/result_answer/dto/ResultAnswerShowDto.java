package com.example.testproject.result_answer.dto;

import com.example.testproject.answer.dto.AnswerShowWithCorrectDto;
import lombok.Data;

import java.util.Set;

@Data
public class ResultAnswerShowDto {

    private String questionText;
    private Integer questionPoints;
    private String questionType;
    private String descriptiveAnswerText;
    private Integer answerRatedPoints;
    private Set<AnswerShowWithCorrectDto> correctAnswers;
    private Set<AnswerShowWithCorrectDto> userAnswers;
}
