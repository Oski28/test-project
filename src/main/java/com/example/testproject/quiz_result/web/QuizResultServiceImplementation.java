package com.example.testproject.quiz_result.web;

import com.example.testproject.quiz_result.model_repo.QuizResult;
import com.example.testproject.quiz_result.model_repo.QuizResultRepository;
import com.example.testproject.shared.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class QuizResultServiceImplementation implements BaseService<QuizResult>, QuizResultService {

    private final QuizResultRepository quizResultRepository;

    @Autowired
    public QuizResultServiceImplementation(QuizResultRepository quizResultRepository) {
        this.quizResultRepository = quizResultRepository;
    }


    @Override
    public Page<QuizResult> getAll(int page, int size, String column, Sort.Direction direction) {
        return null;
    }

    @Override
    public boolean update(Long id, QuizResult entity) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public QuizResult save(QuizResult entity) {
        return this.quizResultRepository.save(entity);
    }

    @Override
    public QuizResult getById(Long id) {
        return null;
    }

    @Override
    public boolean isExists(Long id) {
        return false;
    }
}
