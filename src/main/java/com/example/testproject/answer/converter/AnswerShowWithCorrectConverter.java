package com.example.testproject.answer.converter;

import com.example.testproject.answer.dto.AnswerShowWithCorrectDto;
import com.example.testproject.answer.model_repo.Answer;
import com.example.testproject.shared.BaseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AnswerShowWithCorrectConverter extends BaseConverter<Answer, AnswerShowWithCorrectDto> {

    @Override
    public Function<AnswerShowWithCorrectDto, Answer> toEntity() {
        return null;
    }

    @Override
    public Function<Answer, AnswerShowWithCorrectDto> toDto() {
        return answer -> {
            if (answer == null)
                return null;

            AnswerShowWithCorrectDto dto = new AnswerShowWithCorrectDto();
            dto.setId(answer.getId());
            dto.setCorrect(answer.getCorrect());
            dto.setText(answer.getText());

            return dto;
        };
    }
}
