package com.example.testproject.question.assembler;

import com.example.testproject.question.web.QuestionController;
import com.example.testproject.shared.PaginationProperties;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class QuestionShowAssemblerProperties {

    private final PaginationProperties paginationProperties = new PaginationProperties();

    protected final List<Link> entityLinks = List.of(
            linkTo(methodOn(QuestionController.class)
                    .getAll(paginationProperties.getPAGE(), paginationProperties.getSIZE(),
                            paginationProperties.getCOLUMN(), paginationProperties.getDIRECTION())).withRel("questions")
            .withProfile("TEACHER").withType("GET").withTitle("To fetch all questions paginated"),
            linkTo(methodOn(QuestionController.class).getOne(0L)).withRel("question").withType("GET")
            .withProfile("TEACHER").withTitle("To fetch all information about question")

    );

    protected final List<Link> collectionLinks = List.of(
            linkTo(methodOn(QuestionController.class).getOne(0L)).withRel("question").withType("GET")
                    .withProfile("TEACHER").withTitle("To fetch all information about question"),
            linkTo(methodOn(QuestionController.class)
                    .getAll(paginationProperties.getPAGE(), paginationProperties.getSIZE(),
                            paginationProperties.getCOLUMN(), paginationProperties.getDIRECTION())).withRel("questions")
                    .withProfile("TEACHER").withType("GET").withTitle("To fetch all questions paginated"),
            linkTo(methodOn(QuestionController.class).getAllForAuthTeacherWithFilter(
                    paginationProperties.getPAGE(), paginationProperties.getSIZE(),
                    paginationProperties.getCOLUMN(), paginationProperties.getDIRECTION(), paginationProperties.getFILTER()
            )).withRel("questions created by teacher").withProfile("TEACHER").withTitle("For Teacher to get his/her questions"),
            linkTo(methodOn(QuestionController.class).getQuestionsForTestWithoutCorrect(0L)
            ).withRel("questions drawn for the test").withProfile("USER").withTitle("Get test questions to take the test "),
            linkTo(methodOn(QuestionController.class).getQuestionsForTestWithCorrect(0L)
            ).withRel("questions for the test with correct answers").withProfile("TEACHER").withTitle("Get test questions for teacher with correct answers")
    );
}
