package com.example.testproject.answer.converter;

import com.example.testproject.answer.dto.AnswerShowWithoutCorrectDto;
import com.example.testproject.answer.model_repo.Answer;
import com.example.testproject.shared.BaseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AnswerShowWithoutCorrectConverter extends BaseConverter<Answer, AnswerShowWithoutCorrectDto> {
    @Override
    public Function<AnswerShowWithoutCorrectDto, Answer> toEntity() {
        return null;
    }

    @Override
    public Function<Answer, AnswerShowWithoutCorrectDto> toDto() {
        return answer -> {
            if (answer == null)
                return null;

            AnswerShowWithoutCorrectDto dto = new AnswerShowWithoutCorrectDto();
            dto.setId(answer.getId());
            dto.setText(answer.getText());

            return dto;
        };
    }
}
