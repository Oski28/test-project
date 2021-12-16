package com.example.testproject.result_answer.web;

import com.example.testproject.result_answer.model_repo.ResultAnswer;

import java.util.Set;

public interface ResultAnswerService {

    Integer checkAnswer(Long answerId);

    Integer getMaxPoints(Set<ResultAnswer> resultAnswers);
}
