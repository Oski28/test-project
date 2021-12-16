package com.example.testproject.test.dto;

import com.example.testproject.quiz_result.dto.QuizResultRateDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class TestRateDto {

    @NotNull
    private Long testId;

    private Set<QuizResultRateDto> points;
}
