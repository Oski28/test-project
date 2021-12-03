package com.example.testproject.question.web;

import com.example.testproject.answer.model_repo.Answer;
import com.example.testproject.answer.web.AnswerServiceImplementation;
import com.example.testproject.exceptions.OperationAccessDeniedException;
import com.example.testproject.question.model_repo.Question;
import com.example.testproject.question.model_repo.QuestionRepository;
import com.example.testproject.shared.BaseService;
import com.example.testproject.test.model_repo.Test;
import com.example.testproject.test.web.TestServiceImplementation;
import com.example.testproject.user.model_repo.User;
import com.example.testproject.user.web.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImplementation implements BaseService<Question>, QuestionService {

    private final QuestionRepository questionRepository;

    private UserServiceImplementation userService;

    private TestServiceImplementation testService;

    private AnswerServiceImplementation answerService;

    @Autowired
    public QuestionServiceImplementation(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Autowired
    public void setAnswerService(AnswerServiceImplementation answerService) {
        this.answerService = answerService;
    }

    @Autowired
    public void setUserService(UserServiceImplementation userService) {
        this.userService = userService;
    }

    @Autowired
    public void setTestService(TestServiceImplementation testService) {
        this.testService = testService;
    }

    @Override
    public Page<Question> getAll(int page, int size, String column, Sort.Direction direction) {
        Sort sort = Sort.by(new Sort.Order(direction, column));
        return this.questionRepository.findAll(PageRequest.of(page, size, sort));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public boolean update(Long id, Question entity) {
        if (isExists(id)) {
            User user = this.userService.getAuthUser();
            Question question = getById(id);
            if (!question.getUser().equals(user)) {
                throw new OperationAccessDeniedException("Brak dostępu do pytania o id " + id);
            }
            this.answerService.removeAllFromQuestion(question);
            question.setPoints(entity.getPoints());
            question.setType(entity.getType());
            question.setText(entity.getText());
            for (Answer answer : entity.getAnswers()) {
                answer.setQuestion(question);
                this.answerService.save(answer);
            }
            question.setAnswers(entity.getAnswers());
            return true;
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public boolean delete(Long id) {
        if (isExists(id)) {
            User user = this.userService.getAuthUser();
            Question question = getById(id);
            if (!question.getUser().equals(user)) {
                throw new OperationAccessDeniedException("Brak dostępu do pytania o id " + id);
            }
            this.testService.removeQuestionForAllTests(question);
            this.questionRepository.delete(question);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Question save(Question entity) {
        return this.questionRepository.save(entity);
    }

    @Override
    public Question getById(Long id) {
        if (isExists(id)) {
            return this.questionRepository.getById(id);
        } else {
            return null;
        }
    }

    @Override
    public boolean isExists(Long id) {
        return this.questionRepository.existsById(id);
    }

    @Override
    public Page<Question> getAllForAuthTeacherWithFilter(int page, int size, String column, Sort.Direction sortDir,
                                                         String filter, String name) {
        User user = this.userService.findByUsername(name);
        Sort sort = Sort.by(new Sort.Order(sortDir, column));
        if (filter.equals("")) {
            return this.questionRepository.getAllByUser(user, PageRequest.of(page, size, sort));
        } else {
            return this.questionRepository.getAllByUserAndTextContains(user, filter, PageRequest.of(page, size, sort));
        }
    }

    @Override
    public List<Question> getQuestionsForTest(Long testId) {
        if (!this.testService.isExists(testId)) {
            throw new EntityNotFoundException("Brak testu o podanym id " + testId);
        }
        Test test = this.testService.getById(testId);
        User user = this.userService.getAuthUser();
        if (!test.getUsers().contains(user)) {
            throw new OperationAccessDeniedException("Brak uprawnień do wykonywania testu.");
        }
        if (!(test.getStartDate().isBefore(LocalDateTime.now()) && test.getEndDate().isAfter(LocalDateTime.now()))) {
            throw new OperationAccessDeniedException("Test aktualnie niedostępny.");
        }
        Random random = new Random();
        List<Question> testQuestions = new ArrayList<>(test.getQuestions());
        List<Question> randQuestions = new ArrayList<>();
        for (int i = 0; i < test.getNumberOfQuestions(); i++) {
            int randomNumber = random.nextInt(testQuestions.size());
            Question question = testQuestions.get(randomNumber);
            randQuestions.add(question);
            testQuestions.remove(question);
        }
        for (int i = 0; i < randQuestions.size(); i++) {
            randQuestions.get(i).setAnswers(this.answerService.randAnswers(randQuestions.get(i).getAnswers().stream().collect(Collectors.toList()), randQuestions.get(i).getAnswers().size()).stream().collect(Collectors.toSet()));
        }
        return randQuestions;
    }

    @Override
    public Question getOne(Long id) {
        if (isExists(id)) {
            Question question = getById(id);
            User user = this.userService.getAuthUser();
            if (!question.getUser().equals(user)) {
                throw new OperationAccessDeniedException("Brak dostępu do pytania o id " + id);
            }
            return question;
        }
        throw new EntityNotFoundException("Brak pytania o id " + id);
    }
}
