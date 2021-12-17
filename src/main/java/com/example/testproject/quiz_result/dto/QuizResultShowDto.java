package com.example.testproject.quiz_result.dto;

import com.example.testproject.result_answer.dto.ResultAnswerShowDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class QuizResultShowDto {

    private Long id;
    private String name;
    private Integer totalPoints;
    private Integer maxPoints;
    private LocalDateTime dateOfExecution;
    private Long userId;
    private Set<ResultAnswerShowDto> answers;
}
