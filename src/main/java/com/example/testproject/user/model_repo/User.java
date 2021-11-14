package com.example.testproject.user.model_repo;

import com.example.testproject.question.model_repo.Question;
import com.example.testproject.quiz_result.model_repo.QuizResult;
import com.example.testproject.role.model_repo.Role;
import com.example.testproject.shared.BaseEntity;
import com.example.testproject.test.model_repo.Test;
import lombok.*;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(doNotUseGetters = true)
@NoArgsConstructor
@Entity(name = "user")
@Table(name = "user")
public class User extends BaseEntity {

    @Column(name = "firstname", nullable = false, length = 50)
    @NotBlank(message = "Firstname cannot be blank.")
    @Size(min = 3, max = 50, message = "Firstname must contain between 3 and 50 characters.")
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 50)
    @NotBlank(message = "Lastname cannot be blank.")
    @Size(min = 3, max = 50, message = "Lastname must contain between 3 and 50 characters.")
    private String lastname;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    @NotBlank(message = "Username cannot be blank.")
    @Size(min = 3, max = 50, message = "Username must contain between 3 and 50 characters.")
    private String username;

    @Column(name = "email", unique = true, nullable = false, length = 50)
    @Email(message = "Email must be correct.")
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "add_date", nullable = false)
    @NotNull(message = "Add date cannot be null.")
    private LocalDateTime addDate;

    @Column(name = "delete_date")
    private LocalDateTime deleteDate;

    @Column(name = "enabled", columnDefinition = "boolean default true")
    private Boolean enabled;

    /* RELATIONS */

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "id_user", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id_role", referencedColumnName = "id")},
            indexes = {@Index(name = "user_role_index", columnList = "id_user,id_role", unique = true)})
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Test> createdTests;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<QuizResult> quizResults;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Question> createdQuestions;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Test> availableTests;

}
