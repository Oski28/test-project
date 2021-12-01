package com.example.testproject.quiz_result.model_repo;

import com.example.testproject.result_answer.model_repo.ResultAnswer;
import com.example.testproject.shared.BaseEntity;
import com.example.testproject.test.model_repo.Test;
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
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(doNotUseGetters = true)
@NoArgsConstructor
@Entity(name = "quiz_result")
@Table(name = "quiz_result")
public class QuizResult extends BaseEntity {

    @Column(name = "name", nullable = false, length = 100)
    @NotBlank(message = "Name cannot be blank.")
    @Size(min = 1, max = 100, message = "Name must contain between 1 and 100 characters.")
    private String name;

    @Column(name = "total_points")
    @Min(value = 0, message = "Points must be natural number.")
    private Integer totalPoints;

    @Column(name = "max_points")
    @Min(value = 0, message = "Max points must be natural number.")
    private Integer maxPoints;

    @Column(name = "mark")
    @Min(value = 2, message = "Mark must be more than 1.")
    @Max(value = 5, message = "Mark must be less than 6.")
    private Integer mark;

    @Column(name = "date_of_execution")
    private LocalDateTime dateOfExecution;

    /* RELATIONS */

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(nullable = false, name = "user_id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    @ManyToOne(targetEntity = Test.class, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(nullable = false, name = "test_id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Test test;

    @OneToMany(mappedBy = "quizResult", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ResultAnswer> resultAnswers;
}
