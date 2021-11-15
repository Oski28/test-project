package com.example.testproject.test.model_repo;

import com.example.testproject.question.model_repo.Question;
import com.example.testproject.quiz_result.model_repo.QuizResult;
import com.example.testproject.shared.BaseEntity;
import com.example.testproject.user.model_repo.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(doNotUseGetters = true)
@NoArgsConstructor
@Entity(name = "test")
@Table(name = "test")
public class Test extends BaseEntity {

    @Column(name = "name", nullable = false, length = 100)
    @NotBlank(message = "Name cannot be blank.")
    @Size(min = 1, max = 100, message = "Name must contain between 1 and 100 characters.")
    private String name;

    @Column(name = "number_of_questions")
    @Min(value = 1, message = "Number of questions must be more than 0")
    @Max(value = 100, message = "Number of questions must be less than 101")
    private Integer numberOfQuestions;

    @Column(name = "time")
    @Min(value = 60000, message = "Time must be more or equal 1 minute") // 1 minute
    @Max(value = 7200000, message = "Time must be less or equal 2 hours") // 2 hours
    private Integer time; // in milliseconds

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    /* RELATIONS */

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(nullable = false, name = "organizer_id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "quiz_access",
            joinColumns = {@JoinColumn(name = "id_test", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id_user", referencedColumnName = "id")},
            indexes = {@Index(name = "test_user_index", columnList = "id_test,id_user", unique = true)})
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<User> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "test_question",
            joinColumns = {@JoinColumn(name = "id_test", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id_question", referencedColumnName = "id")},
            indexes = {@Index(name = "test_question_index", columnList = "id_test,id_question", unique = true)})
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Question> questions;

    @OneToMany(mappedBy = "test", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<QuizResult> quizResults;
}
