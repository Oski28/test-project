package com.example.testproject.question.web;

import com.example.testproject.question.model_repo.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;


public interface QuestionService {
    Page<Question> getAllForAuthTeacherWithFilter(int page, int size, String column, Sort.Direction sortDir,
                                                  String filter, String name);

    List<Question> getQuestionsForTest(Long testId);
}
