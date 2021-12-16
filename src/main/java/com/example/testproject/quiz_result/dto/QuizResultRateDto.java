package com.example.testproject.quiz_result.dto;

import com.example.testproject.result_answer.dto.ResultAnswerRateDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class QuizResultRateDto {

    @NotNull
    private Long studentId;

    private Set<ResultAnswerRateDto> answers;


}
