package com.example.testproject.answer.model_repo;

import com.example.testproject.question.model_repo.Question;
import com.example.testproject.shared.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(doNotUseGetters = true)
@NoArgsConstructor
@Entity(name = "answer")
@Table(name = "answer")
public class Answer extends BaseEntity {

    @Column(name = "text", nullable = false, length = 300)
    @NotBlank(message = "Text cannot be blank.")
    @Size(min = 1, max = 300, message = "Text must contain between 1 and 300 characters.")
    private String text;

    @Column(name = "correct")
    private Boolean correct;

    /* RELATIONS */

    @ManyToOne(targetEntity = Question.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, name = "question_id", referencedColumnName = "id")
    private Question question;
}
