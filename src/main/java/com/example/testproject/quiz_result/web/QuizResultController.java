package com.example.testproject.quiz_result.web;

import com.example.testproject.answer.web.AnswerServiceImplementation;
import com.example.testproject.quiz_result.converter.QuizResultConverter;
import com.example.testproject.quiz_result.dto.QuizResultDto;
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


@RestController
@RequestMapping(path = "/api/results")
@CrossOrigin
public class QuizResultController extends BaseController<QuizResult> {

    private final QuizResultConverter quizResultConverter;
    private final QuizResultServiceImplementation quizResultService;
    private final ResultAnswerServiceImplementation resultAnswerService;

    @Autowired
    public QuizResultController(BaseService<QuizResult> service, QuizResultConverter quizResultConverter,
                                QuizResultServiceImplementation quizResultService, ResultAnswerServiceImplementation resultAnswerService) {
        super(service);
        this.quizResultConverter = quizResultConverter;
        this.quizResultService = quizResultService;
        this.resultAnswerService = resultAnswerService;
    }

    /* GET */

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
