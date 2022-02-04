package com.example.testproject.question.web;

import com.example.testproject.question.model_repo.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;


public interface QuestionService {
    Page<Question> getAllForAuthTeacherWithFilter(int page, int size, String column, Sort.Direction sortDir,
                                                  String filter);

    List<Question> getQuestionsForTest(Long testId);

    Question getOne(Long id);
}
