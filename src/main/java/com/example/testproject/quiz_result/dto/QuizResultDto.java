package com.example.testproject.quiz_result.dto;

import com.example.testproject.result_answer.dto.ResultAnswerDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class QuizResultDto {

    @NotNull
    private Long testId;
    Set<ResultAnswerDto> answers;
}
