package com.example.testproject.test.web;

import com.example.testproject.question.model_repo.Question;
import com.example.testproject.test.model_repo.Test;
import com.example.testproject.user.model_repo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface TestService {
    void removeQuestionForAllTests(Question question);

    Page<Test> getAllAvailableTestsForAuthUser(int page, int size, String column, Sort.Direction sortDir);

    Page<Test> getAllCreatedTestsForAuthUser(int page, int size, String column, Sort.Direction sortDir);

    Page<Test> getAllActiveTestsForAuthUser(int page, int size, String column, Sort.Direction sortDir);

    Page<Test> getAllNonactiveTestsForAuthUser(int page, int size, String column, Sort.Direction sortDir);

    Page<Test> getActiveCreatedTestsForAuthUser(int page, int size, String column, Sort.Direction sortDir);

    Page<Test> getNonactiveCreatedTestsForAuthUser(int page, int size, String column, Sort.Direction sortDir);

    Test addQuestionsToTest(List<Long> questionsId, Test test);

    Test addUsersToTest(List<Long> usersId, Test test);

    boolean updateName(Long id, String name);

    boolean updateNumber(Long id, Integer numberOfQuestions);

    boolean updateTime(Long id, Integer time);

    boolean updateStartDate(Long id, LocalDateTime startDate);

    boolean updateEndDate(Long id, LocalDateTime date);

    boolean removeUserFromTestParticipation(User user, Long testId);

    Page<Test> getAllCompletedTestCreatedByUser(int page, int size, String column, Sort.Direction sortDir);

    Page<Test> getAllCompletedAndRatedTestCreatedByUser(int page, int size, String column, Sort.Direction sortDir);

    Page<Test> getAllCompletedAndToRateTestCreatedByUser(int page, int size, String column, Sort.Direction sortDir);
}
