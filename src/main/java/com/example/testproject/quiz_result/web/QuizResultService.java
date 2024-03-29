package com.example.testproject.quiz_result.web;

import com.example.testproject.quiz_result.model_repo.QuizResult;
import com.example.testproject.test.model_repo.Test;
import com.example.testproject.user.model_repo.User;

import java.util.Arrays;
import java.util.List;


public interface QuizResultService {
    List<QuizResult> getAllToEvaluateForTest(Long testId);

    QuizResult getByUserAndTest(User student, Test test);

    void sumPoints(Long id);

    List<QuizResult> getAllRatedForTest(Long testId);

    List<QuizResult> getAllRatedForUser();
}
