package com.example.testproject.question.dto;

import com.example.testproject.answer.dto.AnswerShowDto;
import com.example.testproject.question.model_repo.QuestionType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionShowWithoutCorrectDto extends QuestionShowDto {

    private List<AnswerShowDto> answers;
}
