package com.example.testproject.answer.web;

import com.example.testproject.answer.model_repo.Answer;
import com.example.testproject.answer.model_repo.AnswerRepository;
import com.example.testproject.shared.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class AnswerServiceImplementation implements BaseService<Answer>, AnswerService {

    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerServiceImplementation(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public Page<Answer> getAll(int page, int size, String column, Sort.Direction direction) {
        return null;
    }

    @Override
    public boolean update(Long id, Answer entity) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Answer save(Answer entity) {
        return this.answerRepository.save(entity);
    }

    @Override
    public Answer getById(Long id) {
        return null;
    }

    @Override
    public boolean isExists(Long id) {
        return false;
    }

    @Override
    public List<Answer> randAnswers(List<Answer> answers, int size) {
        Random random = new Random();
        List<Answer> randAnswers = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int randomNumber = random.nextInt(answers.size());
            Answer answer = answers.get(randomNumber);
            randAnswers.add(answer);
            answers.remove(answer);
        }
        return randAnswers;
    }
}
