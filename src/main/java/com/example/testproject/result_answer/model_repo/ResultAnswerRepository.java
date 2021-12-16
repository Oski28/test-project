package com.example.testproject.result_answer.model_repo;

import com.example.testproject.question.model_repo.Question;
import com.example.testproject.quiz_result.model_repo.QuizResult;
import com.example.testproject.shared.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultAnswerRepository extends BaseRepository<ResultAnswer> {
    ResultAnswer getByQuestionAndAndQuizResult(Question question, QuizResult quizResult);
}
