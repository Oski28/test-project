package com.example.testproject.question.dto;

import com.example.testproject.answer.dto.AnswerShowWithoutCorrectDto;
import com.example.testproject.question.model_repo.QuestionType;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Data
public class QuestionShowWithoutCorrectDto {
    private Long id;
    private String text;
    private Integer points;
    @Enumerated(EnumType.STRING)
    private QuestionType type;
    private List<String> testsName;
    private List<AnswerShowWithoutCorrectDto> answers;
}
