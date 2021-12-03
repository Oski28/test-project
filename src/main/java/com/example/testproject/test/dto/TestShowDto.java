package com.example.testproject.test.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TestShowDto {

    private Long id;
    private String name;
    private Integer numberOfQuestions;
    private Integer time; // in milliseconds
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String organizer;
    private Integer availableUsersSize;
    private Integer questionPoolSize;
    private Integer executionSize;
    private List<Long> usersId;
    private List<Long> questionsId;
}
