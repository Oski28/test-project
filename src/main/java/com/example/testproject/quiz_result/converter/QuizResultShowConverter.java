package com.example.testproject.quiz_result.converter;

import com.example.testproject.quiz_result.dto.QuizResultShowDto;
import com.example.testproject.quiz_result.model_repo.QuizResult;
import com.example.testproject.result_answer.converter.ResultAnswerShowConverter;
import com.example.testproject.shared.BaseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizResultShowConverter extends BaseConverter<QuizResult, QuizResultShowDto> {

    private final ResultAnswerShowConverter resultAnswerShowConverter;

    @Override
    public Function<QuizResultShowDto, QuizResult> toEntity() {
        return null;
    }

    @Override
    public Function<QuizResult, QuizResultShowDto> toDto() {
        return quizResult -> {
            if (quizResult == null)
                return null;

            QuizResultShowDto dto = new QuizResultShowDto();

            dto.setId(quizResult.getId());
            dto.setUserId(quizResult.getUser().getId());
            dto.setTotalPoints(quizResult.getTotalPoints());
            dto.setName(quizResult.getName());
            dto.setMaxPoints(quizResult.getMaxPoints());
            dto.setDateOfExecution(quizResult.getDateOfExecution());
            dto.setAnswers(quizResult.getResultAnswers().stream()
                    .map(resultAnswerShowConverter.toDto()).collect(Collectors.toSet()));

            return dto;
        };
    }
}
