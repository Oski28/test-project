package com.example.testproject.result_answer.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ResultAnswerRateDto {

    @NotNull
    private Long questionId;
    @NotNull
    private Integer awardedPoints;
}
