package com.example.testproject.result_answer.model_repo;

import com.example.testproject.question.model_repo.Question;
import com.example.testproject.quiz_result.model_repo.QuizResult;
import com.example.testproject.shared.BaseEntity;
import com.example.testproject.test.model_repo.Test;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(doNotUseGetters = true)
@NoArgsConstructor
@Entity(name = "result_answer")
@Table(name = "result_answer")
public class ResultAnswer extends BaseEntity {

    @Column(name = "text", nullable = false, length = 300)
    @NotBlank(message = "Text cannot be blank.")
    @Size(min = 1, max = 300, message = "Text must contain between 1 and 300 characters.")
    private String text;

    @Column(name = "points")
    private Integer points;

    /* RELATIONS */

    @ManyToOne(targetEntity = QuizResult.class, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(nullable = false, name = "result_id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private QuizResult quizResult;

    @ManyToOne(targetEntity = Question.class, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(nullable = false, name = "question_id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Question question;
}
