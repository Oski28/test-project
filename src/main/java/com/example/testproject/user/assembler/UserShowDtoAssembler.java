package com.example.testproject.user.assembler;

import com.example.testproject.shared.BaseAssembler;
import com.example.testproject.shared.BaseConverter;
import com.example.testproject.shared.PaginationProperties;
import com.example.testproject.user.dto.UserShowDto;
import com.example.testproject.user.model_repo.User;
import com.example.testproject.user.web.UserController;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserShowDtoAssembler extends BaseAssembler<User, UserShowDto> {

    private final PaginationProperties paginationProperties = new PaginationProperties();

    public UserShowDtoAssembler(BaseConverter<User, UserShowDto> converter) {
        super(UserController.class, UserShowDto.class, converter);
    }

    private final List<Link> entityLinks = List.of(
            linkTo(methodOn(UserController.class)
                    .getAll(paginationProperties.getPAGE(), paginationProperties.getSIZE(),
                            paginationProperties.getCOLUMN(), paginationProperties.getDIRECTION(),
                            paginationProperties.getFILTER())).withRel("users")
            .withProfile("USER").withType("GET").withTitle("To fetch all users paginated data")
    );

    private final List<Link> collectionLinks = List.of(
            linkTo(methodOn(UserController.class)
                    .getAll(paginationProperties.getPAGE(), paginationProperties.getSIZE(),
                            paginationProperties.getCOLUMN(), paginationProperties.getDIRECTION(),
                            paginationProperties.getFILTER())).withRel("users")
                    .withProfile("USER").withType("GET").withTitle("To fetch all users paginated data"),
            linkTo(methodOn(UserController.class)
                    .getOne(0L)).withRel("user")
                    .withProfile("USER").withType("GET").withTitle("To fetch all user data")
    );

    @Override
    public UserShowDto toModel(User entity) {
        UserShowDto dto = super.toModel(entity, entityLinks);
        dto.add(linkTo(methodOn(UserController.class).getOne(dto.getId())).withSelfRel());
        return dto;
    }


    public CollectionModel<UserShowDto> toCollectionModel(Page<User> entities, String filter) {
        filter = filter != null ? filter : paginationProperties.getFILTER();
        String column = getColumn(entities.getPageable().getSort().toString());
        String direction = getDirection(entities.getPageable().getSort().toString());
        CollectionModel<UserShowDto> collectionModel = super.toCollectionModel(entities, entityLinks, collectionLinks);
        if (entities.getPageable().hasPrevious()) {
            collectionModel.add(linkTo(methodOn(UserController.class)
                    .getAll(entities.getPageable().getPageNumber() - 1, entities.getPageable().getPageSize(),
                            column, direction, filter)).withRel("prev"));
        }
        collectionModel.add(linkTo(methodOn(UserController.class)
                .getAll(entities.getPageable().getPageNumber(), entities.getPageable().getPageSize(),
                        column, direction, filter)).withSelfRel());
        if (entities.getPageable().getPageNumber() < entities.getTotalPages() - 1) {
            collectionModel.add(linkTo(methodOn(UserController.class)
                    .getAll(entities.getPageable().getPageNumber() + 1, entities.getPageable().getPageSize(),
                            column, direction, filter)).withRel("next"));
        }
        return collectionModel;
    }
}
