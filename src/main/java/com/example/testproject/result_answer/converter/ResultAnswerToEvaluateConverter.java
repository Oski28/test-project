package com.example.testproject.result_answer.converter;

import com.example.testproject.question.model_repo.QuestionType;
import com.example.testproject.result_answer.dto.ResultAnswerToEvaluateDto;
import com.example.testproject.result_answer.model_repo.ResultAnswer;
import com.example.testproject.shared.BaseConverter;
import com.example.testproject.shared.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ResultAnswerToEvaluateConverter extends BaseConverter<ResultAnswer, ResultAnswerToEvaluateDto> {

    @Override
    public Function<ResultAnswerToEvaluateDto, ResultAnswer> toEntity() {
        return null;
    }

    @Override
    public Function<ResultAnswer, ResultAnswerToEvaluateDto> toDto() {
        return resultAnswer -> {
            if (resultAnswer == null)
                return null;

            ResultAnswerToEvaluateDto dto = new ResultAnswerToEvaluateDto();
            dto.setId(resultAnswer.getId());
            dto.setQuestionId(resultAnswer.getQuestion().getId());
            if (resultAnswer.getQuestion().getType().equals(QuestionType.DESCRIPTIVE) ){
                dto.setWrittenOpenedAnswers(resultAnswer.getText());
            } else if (resultAnswer.getQuestion().getType().equals(QuestionType.MULTI)){
                dto.setSelectedClosedAnswersIds(resultAnswer.getAnswers().stream().map(BaseEntity::getId).toArray(Long[]::new));
            }

            return dto;
        };
    }
}
