package com.example.testproject.question.dto;

import com.example.testproject.answer.dto.AnswerDto;
import com.example.testproject.question.model_repo.QuestionType;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class QuestionDto {

    @NotBlank(message = "Text cannot be blank.")
    @Size(min = 1, max = 300, message = "Text must contain between 1 and 300 characters.")
    private String text;

    @Min(value = 1, message = "Points must be more than 0")
    private Integer points;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    private Set<AnswerDto> answers;
}
