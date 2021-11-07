package com.example.testproject.question.model_repo;

import com.example.testproject.answer.model_repo.Answer;
import com.example.testproject.quiz_result.model_repo.QuizResult;
import com.example.testproject.result_answer.model_repo.ResultAnswer;
import com.example.testproject.shared.BaseEntity;
import com.example.testproject.test.model_repo.Test;
import com.example.testproject.user.model_repo.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(doNotUseGetters = true)
@NoArgsConstructor
@Entity(name = "question")
@Table(name = "question")
public class Question extends BaseEntity {

    @Column(name = "text", nullable = false, length = 300)
    @NotBlank(message = "Text cannot be blank.")
    @Size(min = 1, max = 300, message = "Text must contain between 1 and 300 characters.")
    private String text;

    @Column(name = "points")
    @Min(value = 1, message = "Points must be more than 0")
    private Integer points;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 12)
    private QuestionType type;

    /* RELATIONS */

    @ManyToMany(mappedBy = "questions", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Test> tests;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ResultAnswer> resultAnswers;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Answer> answers;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(nullable = false, name = "creator_id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;
}
