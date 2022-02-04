package com.example.testproject.shared;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import java.util.List;

@EnableConfigurationProperties(PaginationProperties.class)
public abstract class BaseAssembler<E, DTO extends RepresentationModel<?>> extends RepresentationModelAssemblerSupport<E, DTO> {

    private final BaseConverter<E, DTO> converter;

    public BaseAssembler(Class<?> controllerClass, Class<DTO> resourceType, BaseConverter<E, DTO> converter) {
        super(controllerClass, resourceType);
        this.converter = converter;
    }

    public DTO toModel(E entity, List<Link> links) {
        DTO dto = converter.toDto().apply(entity);
        dto.add(links);
        return dto;
    }

    public CollectionModel<DTO> toCollectionModel(Page<E> entities, List<Link> entityLinks, List<Link> collectionLinks) {
        CollectionModel<DTO> collectionModel = CollectionModel.of(entities.map(e -> toModel(e, entityLinks)));
        collectionModel.add(collectionLinks);
        return collectionModel;
    }

    public String getColumn(String sort) {
        return sort.substring(0, sort.indexOf(":"));
    }

    public String getDirection(String sort) {
        return sort.substring(sort.indexOf(" ") + 1);
    }
}
