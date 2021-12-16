package com.example.testproject.quiz_result.converter;

import com.example.testproject.quiz_result.dto.QuizResultToEvaluateDto;
import com.example.testproject.quiz_result.model_repo.QuizResult;
import com.example.testproject.result_answer.converter.ResultAnswerToEvaluateConverter;
import com.example.testproject.shared.BaseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizResultToEvaluateConverter extends BaseConverter<QuizResult, QuizResultToEvaluateDto> {

    private final ResultAnswerToEvaluateConverter resultAnswerToEvaluateConverter;

    @Override
    public Function<QuizResultToEvaluateDto, QuizResult> toEntity() {
        return null;
    }

    @Override
    public Function<QuizResult, QuizResultToEvaluateDto> toDto() {
        return quizResult -> {
            if (quizResult == null)
                return null;

            QuizResultToEvaluateDto dto = new QuizResultToEvaluateDto();
            dto.setStudentId(quizResult.getUser().getId());
            dto.setAnswers(quizResult.getResultAnswers().stream()
                    .map(resultAnswer -> this.resultAnswerToEvaluateConverter.toDto()
                            .apply(resultAnswer)).collect(Collectors.toSet()));

            return dto;
        };
    }
}
