package com.example.testproject.test.converter;

import com.example.testproject.shared.BaseConverter;
import com.example.testproject.test.dto.TestDto;
import com.example.testproject.test.model_repo.Test;
import com.example.testproject.test.model_repo.TestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class TestConverter extends BaseConverter<Test, TestDto> {

    @Override
    public Function<TestDto, Test> toEntity() {
        return dto -> {
            if (dto == null)
                return null;

            Test test = new Test();
            test.setName(dto.getName());
            test.setTime(dto.getTime());
            test.setStartDate(dto.getStartDate());
            test.setNumberOfQuestions(dto.getNumberOfQuestions());
            test.setEndDate(dto.getEndDate());
            test.setStatus(TestStatus.TO_RATE);

            return test;
        };
    }

    @Override
    public Function<Test, TestDto> toDto() {
        return null;
    }
}
