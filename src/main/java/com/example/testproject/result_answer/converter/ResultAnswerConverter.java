package com.example.testproject.result_answer.converter;

import com.example.testproject.question.web.QuestionServiceImplementation;
import com.example.testproject.result_answer.dto.ResultAnswerDto;
import com.example.testproject.result_answer.model_repo.ResultAnswer;
import com.example.testproject.result_answer.web.ResultAnswerServiceImplementation;
import com.example.testproject.shared.BaseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ResultAnswerConverter extends BaseConverter<ResultAnswer, ResultAnswerDto> {

    private final QuestionServiceImplementation questionService;
    private final ResultAnswerServiceImplementation resultAnswerService;

    @Override
    public Function<ResultAnswerDto, ResultAnswer> toEntity() {
        return dto -> {
            if (dto == null)
                return null;

            ResultAnswer resultAnswer = new ResultAnswer();

            resultAnswer.setText(dto.getText());
            resultAnswer.setQuestion(questionService.getById(dto.getQuestionId()));
            resultAnswer.setPoints(resultAnswerService.checkAnswer(dto.getAnswerId()));

            return resultAnswer;
        };
    }

    @Override
    public Function<ResultAnswer, ResultAnswerDto> toDto() {
        return null;
    }
}
