package com.example.testproject.test.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class TestTimeDto {
    @Min(value = 60000, message = "Time must be more or equal 1 minute") // 1 minute
    @Max(value = 7200000, message = "Time must be less or equal 2 hours") // 2 hours
    private Integer time; // in milliseconds
}
