package com.example.testproject.test.web;

import com.example.testproject.exceptions.CollectionSizeException;
import com.example.testproject.exceptions.OperationAccessDeniedException;
import com.example.testproject.question.model_repo.Question;
import com.example.testproject.question.web.QuestionServiceImplementation;
import com.example.testproject.shared.BaseEntity;
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

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        if (isExists(id)) {
            User user = userService.getAuthUser();
            Test test = getById(id);
            if (!test.getUser().equals(user)) {
                throw new OperationAccessDeniedException("Brak uprawnień do edycji testu.");
            }
            test.setTime(entity.getTime());
            test.setStartDate(entity.getStartDate());
            test = addQuestionsToTest(entity.getQuestions().stream().map(BaseEntity::getId).collect(Collectors.toList()), test);
            test = addUsersToTest(entity.getUsers().stream().map(BaseEntity::getId).collect(Collectors.toList()), test);
            test.setEndDate(entity.getEndDate());
            test.setNumberOfQuestions(entity.getNumberOfQuestions());
            test.setName(entity.getName());
            return true;
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public boolean delete(Long id) {
        if (isExists(id)) {
            User user = this.userService.getAuthUser();
            Test test = this.getById(id);
            if (!test.getUser().equals(user)) {
                throw new OperationAccessDeniedException("Nie można ususnąć pytania którego nie jest się właścicielem.");
            }
            this.testRepository.delete(test);
            return true;
        }
        return false;
    }

    @Override
    public Test save(Test entity) {
        return this.testRepository.save(entity);
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

    @Override
    public Test addQuestionsToTest(List<Long> questionsId, Test test) {
        User user = this.userService.getAuthUser();
        Set<Question> questionSet = new HashSet<>();
        for (Long aLong : questionsId) {
            Question question = this.questionService.getById(aLong);
            if (question != null) {
                if (question.getUser().equals(user)) {
                    questionSet.add(question);
                } else
                    throw new OperationAccessDeniedException("Nie jesteś właścicielem pytania które chcesz dodać do testu");
            }
        }
        if (test.getNumberOfQuestions() > questionSet.size()) {
            throw new CollectionSizeException("Wystąpił błąd w dodawaniu pytań. Ilość pytań nie może być mniejsza niż zbiór dostępnych pytań.");
        }
        test.setQuestions(questionSet);
        return test;
    }

    @Override
    public Test addUsersToTest(List<Long> usersId, Test test) {
        Set<User> users = new HashSet<>();
        for (Long aLong : usersId) {
            User user = this.userService.getById(aLong);
            if (user != null) {
                users.add(user);
            }
        }
        test.setUsers(users);
        return test;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public boolean updateName(Long id, String name) {
        if (isExists(id)) {
            User user = userService.getAuthUser();
            Test test = getById(id);
            if (test.getUser().equals(user)) {
                test.setName(name);
            } else throw new OperationAccessDeniedException("Brak uprawnień do edycji testu.");
            return true;
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public boolean updateNumber(Long id, Integer numberOfQuestions) {
        if (isExists(id)) {
            User user = userService.getAuthUser();
            Test test = getById(id);
            if (numberOfQuestions > test.getQuestions().size()) {
                throw new CollectionSizeException("Ilość pytań nie może być mniejsza niż zbiór dostępnych pytań.");
            }
            if (test.getUser().equals(user)) {
                test.setNumberOfQuestions(numberOfQuestions);
            } else throw new OperationAccessDeniedException("Brak uprawnień do edycji testu.");
            return true;
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public boolean updateTime(Long id, Integer time) {
        if (isExists(id)) {
            User user = userService.getAuthUser();
            Test test = getById(id);
            if (test.getUser().equals(user)) {
                test.setTime(time);
            } else throw new OperationAccessDeniedException("Brak uprawnień do edycji testu.");
            return true;
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public boolean updateStartDate(Long id, LocalDateTime startDate) {
        if (isExists(id)) {
            User user = userService.getAuthUser();
            Test test = getById(id);
            if (startDate.isAfter(test.getEndDate()) || LocalDateTime.now().isAfter(startDate)) {
                throw new DateTimeException("Data rozpoczęcia musi być z przyszłości i być wcześniej niż data zakończenia");
            }
            if (test.getUser().equals(user)) {
                test.setStartDate(startDate);
            } else throw new OperationAccessDeniedException("Brak uprawnień do edycji testu.");
            return true;
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public boolean updateEndDate(Long id, LocalDateTime date) {
        if (isExists(id)) {
            User user = userService.getAuthUser();
            Test test = getById(id);
            if (test.getStartDate().isAfter(date) || LocalDateTime.now().isAfter(date)) {
                throw new DateTimeException("Data zakończenia musi być z przyszłości i być później niż data rozpoczęcia");
            }
            if (test.getUser().equals(user)) {
                test.setEndDate(date);
            } else throw new OperationAccessDeniedException("Brak uprawnień do edycji testu.");
            return true;
        }
        return false;
    }
}
