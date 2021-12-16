package com.example.testproject.test.model_repo;

import com.example.testproject.question.model_repo.Question;
import com.example.testproject.shared.BaseRepository;
import com.example.testproject.user.model_repo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TestRepository extends BaseRepository<Test> {
    List<Test> findAllByUserAndQuestionsContains(User user, Question question);

    Page<Test> getAllByUsersContainsAndStartDateBeforeAndEndDateAfter(User user, LocalDateTime dateTime1,
                                                                      LocalDateTime dateTime2, Pageable pageable);

    Page<Test> getAllByUsersContainsAndStartDateAfterOrUsersContainsAndEndDateBefore(User user1,
                                                                                     LocalDateTime dateTime1, User user2, LocalDateTime dateTime2, Pageable pageable);

    Page<Test> getAllByUsersContains(User user, Pageable pageable);

    Page<Test> getAllByUser(User user, Pageable pageable);

    Page<Test> getAllByUserAndStartDateBeforeAndEndDateAfter(User user, LocalDateTime dateTime1,
                                                             LocalDateTime dateTime2, Pageable pageable);

    Page<Test> getAllByUserAndStartDateAfterOrUserAndEndDateBefore(User user1, LocalDateTime dateTime1, User user2,
                                                                   LocalDateTime dateTime2, Pageable pageable);

    Page<Test> getAllByUserAndEndDateBefore(User user, LocalDateTime dateTime, Pageable pageable);

    Page<Test> getAllByUserAndEndDateBeforeAndStatus(User user,LocalDateTime dateTime, TestStatus status, Pageable pageable);
}
