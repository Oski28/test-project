package com.example.testproject.test.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class TestDto {

    @NotBlank(message = "Name cannot be blank.")
    @Size(min = 1, max = 100, message = "Name must contain between 1 and 100 characters.")
    private String name;

    @Min(value = 1, message = "Number of questions must be more than 0")
    @Max(value = 100, message = "Number of questions must be less than 101")
    private Integer numberOfQuestions;

    @Min(value = 60000, message = "Time must be more or equal 1 minute") // 1 minute
    @Max(value = 7200000, message = "Time must be less or equal 2 hours") // 2 hours
    private Integer time; // in milliseconds

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private List<Long> usersId;

    private List<Long> questionsId;
}
