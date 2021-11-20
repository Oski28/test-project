package com.example.testproject.test.dto;

import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;

@Data
public class TestDateDto {
    @FutureOrPresent(message = "Date must be future or present date.")
    private LocalDateTime date;
}
