package com.example.testproject.question.converter;

import com.example.testproject.answer.converter.AnswerConverter;
import com.example.testproject.question.dto.QuestionDto;
import com.example.testproject.question.model_repo.Question;
import com.example.testproject.shared.BaseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionConverter extends BaseConverter<Question, QuestionDto> {

    private final AnswerConverter answerConverter;

    @Override
    public Function<QuestionDto, Question> toEntity() {
        return dto -> {
            if (dto == null)
                return null;

            Question question = new Question();

            question.setText(dto.getText());
            question.setPoints(dto.getPoints());
            question.setType(dto.getType());
            question.setAnswers(dto.getAnswers().stream().map(this.answerConverter.toEntity()).collect(Collectors.toSet()));

            return question;
        };
    }

    @Override
    public Function<Question, QuestionDto> toDto() {
        return null;
    }
}
