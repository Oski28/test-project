package com.example.testproject.question.assembler;

import com.example.testproject.question.dto.QuestionShowWithCorrectDto;
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
public class QuestionShowWithCorrectAssembler extends BaseAssembler<Question, QuestionShowWithCorrectDto> {

    private final QuestionShowAssemblerProperties questionShowAssemblerProperties;

    public QuestionShowWithCorrectAssembler(BaseConverter<Question, QuestionShowWithCorrectDto> converter, QuestionShowAssemblerProperties questionShowAssemblerProperties) {
        super(QuestionController.class, QuestionShowWithCorrectDto.class, converter);
        this.questionShowAssemblerProperties = questionShowAssemblerProperties;
    }

    @Override
    public QuestionShowWithCorrectDto toModel(Question entity) {
        QuestionShowWithCorrectDto dto = super.toModel(entity, questionShowAssemblerProperties.entityLinks);
        dto.add(linkTo(methodOn(QuestionController.class).getOne(dto.getId())).withSelfRel());
        return dto;
    }

    public CollectionModel<QuestionShowWithCorrectDto> toCollectionModel(Page<Question> entities) {
        return super.toCollectionModel(entities, questionShowAssemblerProperties.entityLinks
                , questionShowAssemblerProperties.collectionLinks);
    }
}
