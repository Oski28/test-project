package com.example.testproject.quiz_result.web;

import com.example.testproject.exceptions.OperationAccessDeniedException;
import com.example.testproject.quiz_result.model_repo.QuizResult;
import com.example.testproject.quiz_result.model_repo.QuizResultRepository;
import com.example.testproject.result_answer.model_repo.ResultAnswer;
import com.example.testproject.shared.BaseService;
import com.example.testproject.test.model_repo.Test;
import com.example.testproject.test.model_repo.TestStatus;
import com.example.testproject.test.web.TestServiceImplementation;
import com.example.testproject.user.model_repo.User;
import com.example.testproject.user.web.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuizResultServiceImplementation implements BaseService<QuizResult>, QuizResultService {

    private final QuizResultRepository quizResultRepository;

    private UserServiceImplementation userService;

    private TestServiceImplementation testService;

    @Autowired
    public QuizResultServiceImplementation(QuizResultRepository quizResultRepository) {
        this.quizResultRepository = quizResultRepository;
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
        if (isExists(id)) {
            User user = userService.getAuthUser();
            QuizResult quizResult = this.quizResultRepository.getById(id);
            if (!quizResult.getUser().equals(user) && !quizResult.getTest().getUser().equals(user)) {
                throw new OperationAccessDeniedException("Nie masz uprawnień do poglądu rozwiązanego testu.");
            }
            if (quizResult.getTest().getStatus().equals(TestStatus.TO_RATE)) {
                throw new OperationAccessDeniedException("Test nie został w pełni oceniony. Podgląd aktualnie niedostępny");
            }
            return quizResult;
        }
        return null;
    }

    @Override
    public boolean isExists(Long id) {
        return quizResultRepository.existsById(id);
    }

    @Override
    public List<QuizResult> getAllToEvaluateForTest(Long testId) {
        User user = this.userService.getAuthUser();
        Test test = this.testService.getById(testId);
        if (!test.getUser().equals(user)) {
            throw new OperationAccessDeniedException("Nie masz uprawnień do oceniania tych testów");
        }
        if (test.getEndDate().isAfter(LocalDateTime.now())) {
            throw new OperationAccessDeniedException("Test niedostępny do oceniania. Aby rozpocząć ocenianie testów muszą się one zakończyć dla uczniów.");
        }
        List<QuizResult> results = this.quizResultRepository.getAllByTest(test);
        for (QuizResult result : results) {
            result.getResultAnswers().removeIf(resultAnswer -> resultAnswer.getPoints() != null);
        }
        return results;
    }

    @Override
    public QuizResult getByUserAndTest(User student, Test test) {
        return this.quizResultRepository.getByUserAndTest(student, test);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void sumPoints(Long id) {
        Test test = testService.getById(id);
        List<QuizResult> quizResults = this.quizResultRepository.getAllByTest(test);
        for (QuizResult quizResult : quizResults) {
            Integer points = 0;
            for (ResultAnswer resultAnswer : quizResult.getResultAnswers()) {
                points += resultAnswer.getPoints();
            }
            quizResult.setTotalPoints(points);
        }
    }

    @Override
    public List<QuizResult> getAllRatedForTest(Long testId) {
        User user = this.userService.getAuthUser();
        Test test = this.testService.getById(testId);
        if (!test.getUser().equals(user)) {
            throw new OperationAccessDeniedException("Nie masz uprawnień podglądy ocenionych testów");
        }
        if (test.getStatus().equals(TestStatus.TO_RATE)) {
            throw new OperationAccessDeniedException("Test nie został oceniony. Brak możliwości podglądu wyników.");
        }
        return this.quizResultRepository.getAllByTest(test);
    }

    @Override
    public List<QuizResult> getAllRatedForUser() {
        User user = this.userService.getAuthUser();
        List<QuizResult> results = this.quizResultRepository.getAllByUser(user);
        results.removeIf(quizResult -> quizResult.getTest().getStatus().equals(TestStatus.TO_RATE));
        return results;
    }
}
