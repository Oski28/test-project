package com.example.testproject.quiz_result.web;

import com.example.testproject.exceptions.OperationAccessDeniedException;
import com.example.testproject.quiz_result.model_repo.QuizResult;
import com.example.testproject.quiz_result.model_repo.QuizResultRepository;
import com.example.testproject.shared.BaseService;
import com.example.testproject.test.model_repo.Test;
import com.example.testproject.test.web.TestServiceImplementation;
import com.example.testproject.user.model_repo.User;
import com.example.testproject.user.web.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
        return null;
    }

    @Override
    public boolean isExists(Long id) {
        return false;
    }

    @Override
    public List<QuizResult> getAllToEvaluateFromTest(Long testId) {
        User user = this.userService.getAuthUser();
        Test test = this.testService.getById(testId);
        if (!test.getUser().equals(user)) {
            throw new OperationAccessDeniedException("Nie masz uprawnień do oceniania tych testów");
        }
        if (test.getEndDate().isAfter(LocalDateTime.now())) {
            throw new OperationAccessDeniedException("Test niedostępny do oceniania. Aby rozpocząć ocenianie testów muszą się one zakończyć dla uczniów.");
        }
        return this.quizResultRepository.getAllByTest(test);
    }
}
