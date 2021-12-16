package com.example.testproject.result_answer.converter;

import com.example.testproject.answer.model_repo.Answer;
import com.example.testproject.answer.web.AnswerServiceImplementation;
import com.example.testproject.question.model_repo.QuestionType;
import com.example.testproject.question.web.QuestionServiceImplementation;
import com.example.testproject.result_answer.dto.ResultAnswerDto;
import com.example.testproject.result_answer.model_repo.ResultAnswer;
import com.example.testproject.result_answer.web.ResultAnswerServiceImplementation;
import com.example.testproject.shared.BaseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ResultAnswerConverter extends BaseConverter<ResultAnswer, ResultAnswerDto> {

    private final QuestionServiceImplementation questionService;
    private final ResultAnswerServiceImplementation resultAnswerService;
    private final AnswerServiceImplementation answerServiceImplementation;

    @Override
    public Function<ResultAnswerDto, ResultAnswer> toEntity() {
        return dto -> {
            if (dto == null)
                return null;

            ResultAnswer resultAnswer = new ResultAnswer();

            resultAnswer.setText(dto.getText());
            resultAnswer.setQuestion(questionService.getById(dto.getQuestionId()));
            if (resultAnswer.getQuestion().getType().equals(QuestionType.SINGLE)) {
                if (dto.getAnswerId() != null) {
                    resultAnswer.setPoints(resultAnswerService.checkAnswer(dto.getAnswerId()));
                } else resultAnswer.setPoints(0);
            }
            Set<Answer> answerSet = new HashSet<>();
            if (dto.getAnswerIds() != null) {
                for (Long id : dto.getAnswerIds()) {
                    answerSet.add(this.answerServiceImplementation.getById(id));
                }
            } else if (dto.getAnswerId() != null) {
                answerSet.add(this.answerServiceImplementation.getById(dto.getAnswerId()));
            }
            resultAnswer.setAnswers(answerSet);

            return resultAnswer;
        };
    }

    @Override
    public Function<ResultAnswer, ResultAnswerDto> toDto() {
        return null;
    }
}
