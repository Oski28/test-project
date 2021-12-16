package com.example.testproject.question.converter;

import com.example.testproject.answer.converter.AnswerShowWithCorrectConverter;
import com.example.testproject.question.dto.QuestionShowWithCorrectDto;
import com.example.testproject.question.model_repo.Question;
import com.example.testproject.shared.BaseConverter;
import com.example.testproject.test.model_repo.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionShowWithCorrectConverter extends BaseConverter<Question, QuestionShowWithCorrectDto> {

    private final AnswerShowWithCorrectConverter answerShowWithCorrectConverter;

    @Override
    public Function<QuestionShowWithCorrectDto, Question> toEntity() {
        return null;
    }

    @Override
    public Function<Question, QuestionShowWithCorrectDto> toDto() {
        return question -> {
            if (question == null)
                return null;

            QuestionShowWithCorrectDto dto = new QuestionShowWithCorrectDto();
            dto.setId(question.getId());
            dto.setPoints(question.getPoints());
            dto.setText(question.getText());
            dto.setType(question.getType());
            dto.setTestsName(question.getTests().stream().map(Test::getName).collect(Collectors.toList()));
            dto.setAnswers(question.getAnswers().stream().map(this.answerShowWithCorrectConverter.toDto()).collect(Collectors.toList()));

            return dto;
        };
    }
}
