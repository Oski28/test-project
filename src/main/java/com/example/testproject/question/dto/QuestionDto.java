package com.example.testproject.question.dto;

import com.example.testproject.answer.dto.AnswerDto;
import com.example.testproject.answer.model_repo.Answer;
import com.example.testproject.question.model_repo.QuestionType;
import com.example.testproject.result_answer.model_repo.ResultAnswer;
import com.example.testproject.test.model_repo.Test;
import com.example.testproject.user.model_repo.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class QuestionDto {

    @NotBlank(message = "Text cannot be blank.")
    @Size(min = 1, max = 300, message = "Text must contain between 1 and 300 characters.")
    private String text;

    @Min(value = 1, message = "Points must be more than 0")
    private Integer points;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    private List<AnswerDto> answers;
}
