package com.example.testproject.test.converter;

import com.example.testproject.shared.BaseConverter;
import com.example.testproject.test.dto.TestShowDto;
import com.example.testproject.test.model_repo.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class TestShowConverter extends BaseConverter<Test, TestShowDto> {

    @Override
    public Function<TestShowDto, Test> toEntity() {
        return null;
    }

    @Override
    public Function<Test, TestShowDto> toDto() {
        return test -> {
            if (test == null)
                return null;

            TestShowDto dto = new TestShowDto();
            dto.setId(test.getId());
            dto.setTime(test.getTime());
            dto.setStartDate(test.getStartDate());
            dto.setQuestionPoolSize(test.getQuestions().size());
            dto.setOrganizer(test.getUser().getFirstname() + " "
                    + test.getUser().getLastname() + "(" + test.getUser().getUsername() + ")");
            dto.setNumberOfQuestions(test.getNumberOfQuestions());
            dto.setName(test.getName());
            dto.setExecutionSize(test.getQuizResults().size());
            dto.setEndDate(test.getEndDate());
            dto.setAvailableUsersSize(test.getUsers().size());

            return dto;
        };
    }
}
