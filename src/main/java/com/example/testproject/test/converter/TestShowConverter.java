package com.example.testproject.test.converter;

import com.example.testproject.question.model_repo.Question;
import com.example.testproject.shared.BaseConverter;
import com.example.testproject.test.dto.TestShowDto;
import com.example.testproject.test.model_repo.Test;
import com.example.testproject.user.model_repo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;


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
            dto.setUsersId(test.getUsers().stream().map(User::getId).collect(Collectors.toList()));
            dto.setQuestionsId(test.getQuestions().stream().map(Question::getId).collect(Collectors.toList()));
            dto.setStatus(test.getStatus().toString());

            return dto;
        };
    }
}
