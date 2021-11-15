package com.example.testproject.answer.web;

import com.example.testproject.answer.model_repo.Answer;
import com.example.testproject.question.model_repo.Question;

import java.util.List;

public interface AnswerService {
    List<Answer> randAnswers(List<Answer> answers, int size);

    void removeAllFromQuestion(Question question);
}
