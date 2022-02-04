package com.example.testproject.question.dto;

import com.example.testproject.question.model_repo.QuestionType;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Data
public abstract class QuestionShowDto extends RepresentationModel<QuestionShowDto> {

    private Long id;
    private String text;
    private Integer points;
    @Enumerated(EnumType.STRING)
    private QuestionType type;
    private List<String> testsName;
}
