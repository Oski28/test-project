package com.example.testproject.test.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class TestNameDto {
    @NotBlank(message = "Name cannot be blank.")
    @Size(min = 1, max = 100, message = "Name must contain between 1 and 100 characters.")
    private String name;
}
