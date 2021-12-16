package com.example.testproject.quiz_result.converter;

import com.example.testproject.exceptions.OperationAccessDeniedException;
import com.example.testproject.quiz_result.dto.QuizResultDto;
import com.example.testproject.quiz_result.model_repo.QuizResult;
import com.example.testproject.result_answer.converter.ResultAnswerConverter;
import com.example.testproject.result_answer.model_repo.ResultAnswer;
import com.example.testproject.result_answer.web.ResultAnswerServiceImplementation;
import com.example.testproject.shared.BaseConverter;
import com.example.testproject.test.model_repo.Test;
import com.example.testproject.test.web.TestServiceImplementation;
import com.example.testproject.user.model_repo.User;
import com.example.testproject.user.web.UserServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizResultConverter extends BaseConverter<QuizResult, QuizResultDto> {

    private final ResultAnswerServiceImplementation resultAnswerService;
    private final ResultAnswerConverter resultAnswerConverter;
    private final UserServiceImplementation userServiceImplementation;
    private final TestServiceImplementation testServiceImplementation;

    @Override
    public Function<QuizResultDto, QuizResult> toEntity() {
        return dto -> {
            if (dto == null)
                return null;

            QuizResult quizResult = new QuizResult();
            quizResult.setDateOfExecution(LocalDateTime.now());
            Test test = this.testServiceImplementation.getById(dto.getTestId());
            quizResult.setTest(test);
            User user = this.userServiceImplementation.getAuthUser();
            if (!test.getUsers().contains(user)) {
                throw new OperationAccessDeniedException("Nie masz uprawnień by wysłać rozwiązany test o id " + test.getId());
            }
            if (test.getStartDate().isAfter(quizResult.getDateOfExecution()) || test.getEndDate().plusSeconds(2 * test.getTime() / 1000).isBefore(quizResult.getDateOfExecution())) {
                throw new DateTimeException("Czas wysłania testu nie mieści się w ramach czasowych przewidzianych na jego rozwiązanie");
            }
            quizResult.setUser(user);
            quizResult.setName(test.getName() + " " + user.getUsername());
            Set<ResultAnswer> resultAnswerSet = new HashSet<>();
            dto.getAnswers().forEach(resultAnswerDto -> {
                ResultAnswer resultAnswer = this.resultAnswerConverter.toEntity().apply(resultAnswerDto);
                resultAnswer.setQuizResult(quizResult);
                resultAnswerSet.add(resultAnswer);
            });
            quizResult.setResultAnswers(resultAnswerSet);
            quizResult.setMaxPoints(this.resultAnswerService.getMaxPoints(quizResult.getResultAnswers()));

            return quizResult;
        };
    }

    @Override
    public Function<QuizResult, QuizResultDto> toDto() {
        return null;
    }
}
