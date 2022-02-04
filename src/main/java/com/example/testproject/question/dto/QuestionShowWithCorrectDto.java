package com.example.testproject.question.dto;

import com.example.testproject.answer.dto.AnswerShowDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionShowWithCorrectDto extends QuestionShowDto {

    private List<AnswerShowDto> answers;

}
