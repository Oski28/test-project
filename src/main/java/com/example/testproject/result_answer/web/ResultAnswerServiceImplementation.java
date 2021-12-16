package com.example.testproject.result_answer.web;

import com.example.testproject.answer.model_repo.Answer;
import com.example.testproject.answer.web.AnswerServiceImplementation;
import com.example.testproject.question.model_repo.Question;
import com.example.testproject.question.web.QuestionServiceImplementation;
import com.example.testproject.quiz_result.model_repo.QuizResult;
import com.example.testproject.result_answer.model_repo.ResultAnswer;
import com.example.testproject.result_answer.model_repo.ResultAnswerRepository;
import com.example.testproject.shared.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ResultAnswerServiceImplementation implements BaseService<ResultAnswer>, ResultAnswerService {

    private final ResultAnswerRepository resultAnswerRepository;

    private AnswerServiceImplementation answerService;

    private QuestionServiceImplementation questionService;

    @Autowired
    public ResultAnswerServiceImplementation(ResultAnswerRepository resultAnswerRepository) {
        this.resultAnswerRepository = resultAnswerRepository;
    }

    @Autowired
    public void setAnswerService(AnswerServiceImplementation answerService) {
        this.answerService = answerService;
    }

    @Autowired
    public void setQuestionService(QuestionServiceImplementation questionService) {
        this.questionService = questionService;
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
        return this.resultAnswerRepository.save(entity);
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

    @Override
    public Integer getMaxPoints(Set<ResultAnswer> resultAnswers) {
        AtomicReference<Integer> sum = new AtomicReference<>(0);
        resultAnswers.forEach(resultAnswer -> {
            sum.updateAndGet(v -> v + resultAnswer.getQuestion().getPoints());
        });
        return sum.get();
    }

    @Override
    public ResultAnswer getByQuestionIdAndQuizResult(Long questionId, QuizResult quizResult) {
        Question question = this.questionService.getById(questionId);
        return this.resultAnswerRepository.getByQuestionAndAndQuizResult(question, quizResult);
    }
}
