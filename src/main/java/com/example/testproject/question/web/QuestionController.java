package com.example.testproject.question.web;

import com.example.testproject.answer.model_repo.Answer;
import com.example.testproject.answer.web.AnswerServiceImplementation;
import com.example.testproject.question.assembler.QuestionShowWithoutCorrectAssembler;
import com.example.testproject.question.converter.QuestionConverter;
import com.example.testproject.question.assembler.QuestionShowWithCorrectAssembler;
import com.example.testproject.question.dto.QuestionDto;
import com.example.testproject.question.dto.QuestionShowWithCorrectDto;
import com.example.testproject.question.dto.QuestionShowWithoutCorrectDto;
import com.example.testproject.question.model_repo.Question;
import com.example.testproject.shared.BaseController;
import com.example.testproject.shared.BaseService;
import com.example.testproject.shared.hateoas_response.Embedded;
import com.example.testproject.shared.hateoas_response.PageInfo;
import com.example.testproject.shared.hateoas_response.PaginationAndHateoasResponse;
import com.example.testproject.user.model_repo.User;
import com.example.testproject.user.web.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(path = "/api/questions")
@CrossOrigin
public class QuestionController extends BaseController<Question> {

    private final QuestionServiceImplementation questionService;
    private final QuestionConverter questionConverter;
    private final UserServiceImplementation userService;
    private final AnswerServiceImplementation answerService;
    private final QuestionShowWithCorrectAssembler correctAssembler;
    private final QuestionShowWithoutCorrectAssembler withoutCorrectAssembler;

    @Autowired
    public QuestionController(BaseService<Question> service, QuestionServiceImplementation questionService,
                              QuestionConverter questionConverter,
                              UserServiceImplementation userService, AnswerServiceImplementation answerService,
                              QuestionShowWithCorrectAssembler correctAssembler, QuestionShowWithoutCorrectAssembler withoutCorrectAssembler) {
        super(service);
        this.questionService = questionService;
        this.questionConverter = questionConverter;
        this.userService = userService;
        this.answerService = answerService;
        this.correctAssembler = correctAssembler;
        this.withoutCorrectAssembler = withoutCorrectAssembler;
    }

    /* GET */

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<QuestionShowWithCorrectDto> getOne(@PathVariable final Long id) {
        return ResponseEntity.ok(correctAssembler.toModel(this.questionService.getOne(id)));
    }

    @GetMapping(value = "", produces = "application/hal+json")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<PaginationAndHateoasResponse<QuestionShowWithCorrectDto>> getAll(@RequestParam(defaultValue = "0") final int page,
                                                                                           @RequestParam(defaultValue = "20") final int size,
                                                                                           @RequestParam(defaultValue = "id") final String column,
                                                                                           @RequestParam(defaultValue = "ASC") final String direction) {
        Sort.Direction sortDir = direction.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Page<Question> questionPage = this.questionService.getAll(page, size, column, sortDir);
        CollectionModel<QuestionShowWithCorrectDto> collectionModel
                = correctAssembler.toCollectionModel(questionPage);
        return new ResponseEntity<>(
                new PaginationAndHateoasResponse<>(
                        new Embedded<>(collectionModel.getContent(), new PageInfo(questionPage)),
                        collectionModel.getLinks().toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/teacher", produces = "application/hal+json")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<PaginationAndHateoasResponse<QuestionShowWithCorrectDto>> getAllForAuthTeacherWithFilter(@RequestParam(defaultValue = "0") final int page,
                                                                                                                   @RequestParam(defaultValue = "20") final int size,
                                                                                                                   @RequestParam(defaultValue = "id") final String column,
                                                                                                                   @RequestParam(defaultValue = "ASC") final String direction,
                                                                                                                   @RequestParam(defaultValue = "") final String filter) {
        Sort.Direction sortDir = direction.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Page<Question> questionPage = this.questionService.getAllForAuthTeacherWithFilter(page, size, column, sortDir, filter);
        CollectionModel<QuestionShowWithCorrectDto> collectionModel
                = correctAssembler.toCollectionModel(questionPage);
        return new ResponseEntity<>(
                new PaginationAndHateoasResponse<>(
                        new Embedded<>(collectionModel.getContent(), new PageInfo(questionPage)),
                        collectionModel.getLinks().toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/test/{testId}/correct", produces = "application/hal+json")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<CollectionModel<QuestionShowWithCorrectDto>> getQuestionsForTestWithCorrect(@PathVariable Long testId) {
        return ResponseEntity.ok(correctAssembler.toCollectionModel(new PageImpl<>(this.questionService.getQuestionsForTest(testId))));
    }

    @GetMapping(value = "/test/{testId}", produces = "application/hal+json")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CollectionModel<QuestionShowWithoutCorrectDto>> getQuestionsForTestWithoutCorrect(@PathVariable Long testId) {
        return ResponseEntity.ok(withoutCorrectAssembler.toCollectionModel(new PageImpl<>(this.questionService.getQuestionsForTest(testId))));
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
