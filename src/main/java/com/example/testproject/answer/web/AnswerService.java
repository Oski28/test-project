package com.example.testproject.answer.web;

import com.example.testproject.answer.model_repo.Answer;

import java.util.List;

public interface AnswerService {
    List<Answer> randAnswers(List<Answer> answers, int size);
}
