package com.example.testproject.result_answer.converter;

import com.example.testproject.answer.converter.AnswerShowWithCorrectConverter;
import com.example.testproject.result_answer.dto.ResultAnswerShowDto;
import com.example.testproject.result_answer.model_repo.ResultAnswer;
import com.example.testproject.shared.BaseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResultAnswerShowConverter extends BaseConverter<ResultAnswer, ResultAnswerShowDto> {

    private final AnswerShowWithCorrectConverter answerShowWithCorrectConverter;

    @Override
    public Function<ResultAnswerShowDto, ResultAnswer> toEntity() {
        return null;
    }

    @Override
    public Function<ResultAnswer, ResultAnswerShowDto> toDto() {
        return resultAnswer -> {
            if (resultAnswer == null)
                return null;

            ResultAnswerShowDto dto = new ResultAnswerShowDto();
            dto.setAnswerRatedPoints(resultAnswer.getPoints());
            dto.setUserAnswers(resultAnswer.getAnswers().stream()
                    .map(answerShowWithCorrectConverter.toDto()).collect(Collectors.toSet()));
            dto.setQuestionType(resultAnswer.getQuestion().getType().toString());
            dto.setQuestionText(resultAnswer.getQuestion().getText());
            dto.setQuestionPoints(resultAnswer.getQuestion().getPoints());
            dto.setDescriptiveAnswerText(resultAnswer.getText());
            dto.setCorrectAnswers(resultAnswer.getQuestion().getAnswers().stream()
                    .map(answerShowWithCorrectConverter.toDto()).collect(Collectors.toSet()));

            return dto;
        };
    }
}
