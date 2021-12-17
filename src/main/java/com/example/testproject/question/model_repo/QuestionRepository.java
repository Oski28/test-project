package com.example.testproject.question.model_repo;

import com.example.testproject.shared.BaseRepository;
import com.example.testproject.user.model_repo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionRepository extends BaseRepository<Question> {

    Page<Question> getAllByUser(User user, Pageable pageable);

    Page<Question> getAllByUserAndTextContains(User user, String filter, Pageable pageable);

}
