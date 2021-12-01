package com.example.testproject.result_answer.web;

import com.example.testproject.answer.model_repo.Answer;
import com.example.testproject.answer.web.AnswerServiceImplementation;
import com.example.testproject.result_answer.model_repo.ResultAnswer;
import com.example.testproject.result_answer.model_repo.ResultAnswerRepository;
import com.example.testproject.shared.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ResultAnswerServiceImplementation implements BaseService<ResultAnswer>, ResultAnswerService {

    private final ResultAnswerRepository resultAnswerRepository;

    private AnswerServiceImplementation answerService;

    @Autowired
    public ResultAnswerServiceImplementation(ResultAnswerRepository resultAnswerRepository) {
        this.resultAnswerRepository = resultAnswerRepository;
    }

    @Autowired
    public void setAnswerService(AnswerServiceImplementation answerService) {
        this.answerService = answerService;
    }

    @Override
    public Page<ResultAnswer> getAll(int page, int size, String column, Sort.Direction direction) {
        return null;
    }

    @Override
    public boolean update(Long id, ResultAnswer entity) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public ResultAnswer save(ResultAnswer entity) {
        return null;
    }

    @Override
    public ResultAnswer getById(Long id) {
        if (isExists(id)) {
            return this.resultAnswerRepository.getById(id);
        }
        return null;
    }

    @Override
    public boolean isExists(Long id) {
        return this.resultAnswerRepository.existsById(id);
    }

    @Override
    public Integer checkAnswer(Long answerId) {
        Answer answer = this.answerService.getById(answerId);
        if (answer != null && answer.getCorrect()) {
            return answer.getQuestion().getPoints();
        }
        return 0;
    }
}