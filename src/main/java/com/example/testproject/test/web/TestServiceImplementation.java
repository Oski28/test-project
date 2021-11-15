package com.example.testproject.test.web;

import com.example.testproject.exceptions.CollectionSizeException;
import com.example.testproject.question.model_repo.Question;
import com.example.testproject.question.web.QuestionServiceImplementation;
import com.example.testproject.shared.BaseService;
import com.example.testproject.test.model_repo.Test;
import com.example.testproject.test.model_repo.TestRepository;
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

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TestServiceImplementation implements BaseService<Test>, TestService {

    private final TestRepository testRepository;

    private UserServiceImplementation userService;

    private QuestionServiceImplementation questionService;

    @Autowired
    public TestServiceImplementation(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Autowired
    public void setUserService(UserServiceImplementation userService) {
        this.userService = userService;
    }

    @Autowired
    public void setQuestionService(QuestionServiceImplementation questionService) {
        this.questionService = questionService;
    }

    @Override
    public Page<Test> getAll(int page, int size, String column, Sort.Direction direction) {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public boolean update(Long id, Test entity) {
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Test save(Test entity) {
        return null;
    }

    @Override
    public Test getById(Long id) {
        if (isExists(id)) {
            return this.testRepository.getById(id);
        } else {
            return null;
        }
    }

    @Override
    public boolean isExists(Long id) {
        return this.testRepository.existsById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void removeQuestionForAllTests(Question question) {
        User user = this.userService.getAuthUser();
        List<Test> tests = this.testRepository.findAllByUserAndQuestionsContains(user, question);
        for (Test test : tests) {
            if (test.getNumberOfQuestions().compareTo(test.getQuestions().size() - 1) > 0) {
                throw new CollectionSizeException("Po usunięciu pytania ilość pytań w zbiorze losującym będzie mniejsza" +
                        " niż ilość pytań w teście.");
            } else {
                test.getQuestions().remove(question);
            }
        }
    }

    @Override
    public Page<Test> getAllAvailableTestsForAuthUser(int page, int size, String column, Sort.Direction sortDir) {
        Sort sort = Sort.by(new Sort.Order(sortDir, column));
        User user = this.userService.getAuthUser();
        return this.testRepository.getAllByUsersContains(user, PageRequest.of(page, size, sort));
    }

    @Override
    public Page<Test> getAllCreatedTestsForAuthUser(int page, int size, String column, Sort.Direction sortDir) {
        Sort sort = Sort.by(new Sort.Order(sortDir, column));
        User user = this.userService.getAuthUser();
        return this.testRepository.getAllByUser(user, PageRequest.of(page, size, sort));
    }

    @Override
    public Page<Test> getAllActiveTestsForAuthUser(int page, int size, String column, Sort.Direction sortDir) {
        Sort sort = Sort.by(new Sort.Order(sortDir, column));
        User user = this.userService.getAuthUser();
        return this.testRepository.getAllByUsersContainsAndStartDateBeforeAndEndDateAfter
                (user, LocalDateTime.now(), LocalDateTime.now(), PageRequest.of(page, size, sort));
    }

    @Override
    public Page<Test> getAllNonactiveTestsForAuthUser(int page, int size, String column, Sort.Direction sortDir) {
        Sort sort = Sort.by(new Sort.Order(sortDir, column));
        User user = this.userService.getAuthUser();
        return this.testRepository.getAllByUsersContainsAndStartDateAfterOrUsersContainsAndEndDateBefore(user, LocalDateTime.now(),
                user, LocalDateTime.now(), PageRequest.of(page, size, sort));
    }

    @Override
    public Page<Test> getActiveCreatedTestsForAuthUser(int page, int size, String column, Sort.Direction sortDir) {
        Sort sort = Sort.by(new Sort.Order(sortDir, column));
        User user = this.userService.getAuthUser();
        return this.testRepository.getAllByUserAndStartDateBeforeAndEndDateAfter(user, LocalDateTime.now(),
                LocalDateTime.now(), PageRequest.of(page, size, sort));
    }

    @Override
    public Page<Test> getNonactiveCreatedTestsForAuthUser(int page, int size, String column, Sort.Direction sortDir) {
        Sort sort = Sort.by(new Sort.Order(sortDir, column));
        User user = this.userService.getAuthUser();
        return this.testRepository.getAllByUserAndStartDateAfterOrUserAndEndDateBefore(user, LocalDateTime.now(),
                user, LocalDateTime.now(), PageRequest.of(page, size, sort));
    }
}
