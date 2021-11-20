package com.example.testproject.test.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class TestNumberDto {

    @Min(value = 1, message = "Number of questions must be more than 0")
    @Max(value = 100, message = "Number of questions must be less than 101")
    private Integer numberOfQuestions;
}
