package com.example.testproject.quiz_result.web;

import com.example.testproject.quiz_result.converter.QuizResultConverter;
import com.example.testproject.quiz_result.converter.QuizResultShowConverter;
import com.example.testproject.quiz_result.converter.QuizResultToEvaluateConverter;
import com.example.testproject.quiz_result.dto.QuizResultDto;
import com.example.testproject.quiz_result.dto.QuizResultShowDto;
import com.example.testproject.quiz_result.dto.QuizResultToEvaluateDto;
import com.example.testproject.quiz_result.model_repo.QuizResult;
import com.example.testproject.result_answer.model_repo.ResultAnswer;
import com.example.testproject.result_answer.web.ResultAnswerServiceImplementation;
import com.example.testproject.shared.BaseController;
import com.example.testproject.shared.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(path = "/api/results")
@CrossOrigin
public class QuizResultController extends BaseController<QuizResult> {

    private final QuizResultConverter quizResultConverter;
    private final QuizResultServiceImplementation quizResultService;
    private final ResultAnswerServiceImplementation resultAnswerService;
    private final QuizResultToEvaluateConverter quizResultToEvaluateConverter;
    private final QuizResultShowConverter quizResultShowConverter;

    @Autowired
    public QuizResultController(BaseService<QuizResult> service, QuizResultConverter quizResultConverter,
                                QuizResultServiceImplementation quizResultService,
                                ResultAnswerServiceImplementation resultAnswerService,
                                QuizResultToEvaluateConverter quizResultToEvaluateConverter, QuizResultShowConverter quizResultShowConverter) {
        super(service);
        this.quizResultConverter = quizResultConverter;
        this.quizResultService = quizResultService;
        this.resultAnswerService = resultAnswerService;
        this.quizResultToEvaluateConverter = quizResultToEvaluateConverter;
        this.quizResultShowConverter = quizResultShowConverter;
    }

    /* GET */
    @GetMapping("/{testId}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<List<QuizResultToEvaluateDto>> getAllResultsToEvaluate(@PathVariable Long testId) {
        return ResponseEntity.ok(this.quizResultService.getAllToEvaluateForTest(testId).stream()
                .map(this.quizResultToEvaluateConverter.toDto()).collect(Collectors.toList()));
    }

    @GetMapping("/{testId}/rated")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<List<QuizResultShowDto>> getAllRatedResultsForTest(@PathVariable Long testId) {
        return ResponseEntity.ok(this.quizResultService.getAllRatedForTest(testId).stream()
                .map(this.quizResultShowConverter.toDto()).collect(Collectors.toList()));
    }

    @GetMapping("")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<QuizResultShowDto>> getAllRatedResultsForUser() {
        return ResponseEntity.ok(this.quizResultService.getAllRatedForUser().stream()
                .map(this.quizResultShowConverter.toDto()).collect(Collectors.toList()));
    }

    @GetMapping("/{id}/one")
    public ResponseEntity<QuizResultShowDto> getOne(@PathVariable final Long id) {
        return ResponseEntity.ok(this.quizResultShowConverter.toDto().apply(this.quizResultService.getById(id)));
    }


    /* POST */
    @PostMapping("")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> create(@RequestBody @Valid final QuizResultDto dto) {
        QuizResult quizResult = this.quizResultConverter.toEntity().apply(dto);
        QuizResult savedResult = this.quizResultService.save(quizResult);
        for (ResultAnswer resultAnswer : quizResult.getResultAnswers()) {
            resultAnswer.setQuizResult(savedResult);
            this.resultAnswerService.save(resultAnswer);
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedResult.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    /* PATCH */

    /* PUT */

    /* DELETE */
}
