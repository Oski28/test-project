package com.example.testproject.answer.converter;

import com.example.testproject.answer.dto.AnswerDto;
import com.example.testproject.answer.model_repo.Answer;
import com.example.testproject.shared.BaseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AnswerConverter extends BaseConverter<Answer, AnswerDto> {

    @Override
    public Function<AnswerDto, Answer> toEntity() {
        return dto -> {
            if (dto == null)
                return null;

            Answer answer = new Answer();

            answer.setText(dto.getText());
            answer.setCorrect(dto.getCorrect());

            return answer;
        };
    }

    @Override
    public Function<Answer, AnswerDto> toDto() {
        return null;
    }
}
