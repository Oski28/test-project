package com.example.testproject.question.assembler;

import com.example.testproject.question.dto.QuestionShowWithoutCorrectDto;
import com.example.testproject.question.model_repo.Question;
import com.example.testproject.question.web.QuestionController;
import com.example.testproject.shared.BaseAssembler;
import com.example.testproject.shared.BaseConverter;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class QuestionShowWithoutCorrectAssembler extends BaseAssembler<Question, QuestionShowWithoutCorrectDto> {

    private final QuestionShowAssemblerProperties questionShowAssemblerProperties;

    public QuestionShowWithoutCorrectAssembler(BaseConverter<Question, QuestionShowWithoutCorrectDto> converter, QuestionShowAssemblerProperties questionShowAssemblerProperties) {
        super(QuestionController.class, QuestionShowWithoutCorrectDto.class, converter);
        this.questionShowAssemblerProperties = questionShowAssemblerProperties;
    }

    @Override
    public QuestionShowWithoutCorrectDto toModel(Question entity) {
        QuestionShowWithoutCorrectDto dto = super.toModel(entity, questionShowAssemblerProperties.entityLinks);
        dto.add(linkTo(methodOn(QuestionController.class).getOne(dto.getId())).withSelfRel());
        return dto;
    }

    public CollectionModel<QuestionShowWithoutCorrectDto> toCollectionModel(Page<Question> entities) {
        return super.toCollectionModel(entities, questionShowAssemblerProperties.entityLinks
                , questionShowAssemblerProperties.collectionLinks);
    }
}
