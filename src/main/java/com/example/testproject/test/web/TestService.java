package com.example.testproject.test.web;

import com.example.testproject.question.model_repo.Question;
import com.example.testproject.test.model_repo.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.Optional;


public interface TestService {
    void removeQuestionForAllTests(Question question);

    Page<Test> getAllAvailableTestsForAuthUser(int page, int size, String column, Sort.Direction sortDir);

    Page<Test> getAllCreatedTestsForAuthUser(int page, int size, String column, Sort.Direction sortDir);

    Page<Test> getAllActiveTestsForAuthUser(int page, int size, String column, Sort.Direction sortDir);

    Page<Test> getAllNonactiveTestsForAuthUser(int page, int size, String column, Sort.Direction sortDir);

    Page<Test> getActiveCreatedTestsForAuthUser(int page, int size, String column, Sort.Direction sortDir);

    Page<Test> getNonactiveCreatedTestsForAuthUser(int page, int size, String column, Sort.Direction sortDir);
}
