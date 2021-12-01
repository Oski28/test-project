package com.example.testproject.quiz_result.converter;

import com.example.testproject.quiz_result.dto.QuizResultDto;
import com.example.testproject.quiz_result.model_repo.QuizResult;
import com.example.testproject.result_answer.converter.ResultAnswerConverter;
import com.example.testproject.result_answer.web.ResultAnswerServiceImplementation;
import com.example.testproject.shared.BaseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class QuizResultConverter extends BaseConverter<QuizResult, QuizResultDto> {

    private final ResultAnswerServiceImplementation resultAnswerService;
    private final ResultAnswerConverter resultAnswerConverter;

    @Override
    public Function<QuizResultDto, QuizResult> toEntity() {
        return dto -> {
            if (dto == null)
                return null;

            QuizResult quizResult = new QuizResult();
            quizResult.setDateOfExecution(LocalDateTime.now());
            //TODO
            //quizResult.setResultAnswers(dto.getUserAnswers().forEach(resultAnswerConverter.toEntity()::apply));

            return quizResult;
        };
    }

    @Override
    public Function<QuizResult, QuizResultDto> toDto() {
        return null;
    }
}
