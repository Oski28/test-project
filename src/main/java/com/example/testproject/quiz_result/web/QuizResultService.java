package com.example.testproject.quiz_result.web;

import com.example.testproject.quiz_result.model_repo.QuizResult;

import java.util.List;


public interface QuizResultService {
    List<QuizResult> getAllToEvaluateFromTest(Long testId);
}
