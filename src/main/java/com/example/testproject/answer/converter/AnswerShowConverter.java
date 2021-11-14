package com.example.testproject.answer.converter;

import com.example.testproject.answer.dto.AnswerShowDto;
import com.example.testproject.answer.model_repo.Answer;
import com.example.testproject.shared.BaseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AnswerShowConverter extends BaseConverter<Answer, AnswerShowDto> {

    @Override
    public Function<AnswerShowDto, Answer> toEntity() {
        return null;
    }

    @Override
    public Function<Answer, AnswerShowDto> toDto() {
        return answer -> {
            if (answer == null)
                return null;

            AnswerShowDto dto = new AnswerShowDto();
            dto.setId(answer.getId());
            dto.setCorrect(answer.getCorrect());
            dto.setText(answer.getText());

            return dto;
        };
    }
}
