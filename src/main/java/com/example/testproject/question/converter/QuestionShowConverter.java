package com.example.testproject.question.converter;

import com.example.testproject.answer.converter.AnswerShowConverter;
import com.example.testproject.question.dto.QuestionShowDto;
import com.example.testproject.question.model_repo.Question;
import com.example.testproject.shared.BaseConverter;
import com.example.testproject.test.model_repo.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionShowConverter extends BaseConverter<Question, QuestionShowDto> {

    private final AnswerShowConverter answerShowConverter;

    @Override
    public Function<QuestionShowDto, Question> toEntity() {
        return null;
    }

    @Override
    public Function<Question, QuestionShowDto> toDto() {
        return question -> {
            if (question == null)
                return null;

            QuestionShowDto dto = new QuestionShowDto();
            dto.setId(question.getId());
            dto.setPoints(question.getPoints());
            dto.setText(question.getText());
            dto.setType(question.getType());
            dto.setTestsName(question.getTests().stream().map(Test::getName).collect(Collectors.toList()));
            dto.setAnswers(question.getAnswers().stream().map(this.answerShowConverter.toDto()).collect(Collectors.toList()));

            return dto;
        };
    }
}
