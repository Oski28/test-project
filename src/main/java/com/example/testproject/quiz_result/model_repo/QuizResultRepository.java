package com.example.testproject.quiz_result.model_repo;

import com.example.testproject.shared.BaseRepository;
import com.example.testproject.test.model_repo.Test;
import com.example.testproject.user.model_repo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizResultRepository extends BaseRepository<QuizResult> {

    List<QuizResult> getAllByTest(Test test);

    QuizResult getByUserAndTest(User user, Test test);

    List<QuizResult> getAllByUser(User user);
}
