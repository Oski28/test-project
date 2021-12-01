package com.example.testproject.question.web;

import com.example.testproject.answer.model_repo.Answer;
import com.example.testproject.answer.web.AnswerServiceImplementation;
import com.example.testproject.question.converter.QuestionConverter;
import com.example.testproject.question.converter.QuestionShowConverter;
import com.example.testproject.question.dto.QuestionDto;
import com.example.testproject.question.dto.QuestionShowDto;
import com.example.testproject.question.model_repo.Question;
import com.example.testproject.shared.BaseController;
import com.example.testproject.shared.BaseService;
import com.example.testproject.user.model_repo.User;
import com.example.testproject.user.web.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/questions")
@CrossOrigin
public class QuestionController extends BaseController<Question> {

    private final QuestionServiceImplementation questionService;
    private final QuestionShowConverter questionShowConverter;
    private final QuestionConverter questionConverter;
    private final UserServiceImplementation userService;
    private final AnswerServiceImplementation answerService;

    @Autowired
    public QuestionController(BaseService<Question> service, QuestionServiceImplementation questionService,
                              QuestionShowConverter questionShowConverter, QuestionConverter questionConverter,
                              UserServiceImplementation userService, AnswerServiceImplementation answerService) {
        super(service);
        this.questionService = questionService;
        this.questionShowConverter = questionShowConverter;
        this.questionConverter = questionConverter;
        this.userService = userService;
        this.answerService = answerService;
    }

    /* GET */

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<QuestionShowDto> getOne(@PathVariable final Long id) {
        return super.getOne(id, this.questionShowConverter.toDto());
    }

    @GetMapping("")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Page<QuestionShowDto>> getAll(@RequestParam(defaultValue = "0") final int page,
                                                        @RequestParam(defaultValue = "20") final int size,
                                                        @RequestParam(defaultValue = "id") final String column,
                                                        @RequestParam(defaultValue = "ASC") final String direction) {
        Sort.Direction sortDir = direction.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(this.questionService.getAll(page, size, column, sortDir).
                map(this.questionShowConverter.toDto()));
    }

    @GetMapping("/teacher")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Page<QuestionShowDto>> getAllForAuthTeacherWithFilter(@RequestParam(defaultValue = "0") final int page,
                                                                                @RequestParam(defaultValue = "20") final int size,
                                                                                @RequestParam(defaultValue = "id") final String column,
                                                                                @RequestParam(defaultValue = "ASC") final String direction,
                                                                                @RequestParam(defaultValue = "") final String filter) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Sort.Direction sortDir = direction.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(this.questionService.getAllForAuthTeacherWithFilter(page, size, column, sortDir, filter, authentication.getName())
                .map(this.questionShowConverter.toDto()));
    }

    @GetMapping("/test/{testId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<QuestionShowDto>> getQuestionsForTest(@PathVariable Long testId) {
        return ResponseEntity.ok(this.questionService.getQuestionsForTest(testId).stream()
                .map(this.questionShowConverter.toDto()).collect(Collectors.toList()));
    }

    /* POST */

    @PostMapping("")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> create(@RequestBody @Valid final QuestionDto dto) {

        Question question = this.questionConverter.toEntity().apply(dto);
        User user = this.userService.getAuthUser();
        question.setUser(user);

        Question savedQuestion = this.questionService.save(question);

        for (Answer answer : question.getAnswers()) {
            answer.setQuestion(savedQuestion);
            this.answerService.save(answer);
        }

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedQuestion.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    /* PATCH */

    /* PUT */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> update(@PathVariable final Long id, @RequestBody @Valid final QuestionDto dto) {
        return super.update(id, this.questionConverter.toEntity().apply(dto));
    }

    /* DELETE */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        if (this.questionService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
