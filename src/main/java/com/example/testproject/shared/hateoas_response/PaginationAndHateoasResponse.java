package com.example.testproject.shared.hateoas_response;

import lombok.Data;
import org.springframework.hateoas.Link;

import java.util.List;

@Data
public class PaginationAndHateoasResponse<T> {
    private final Embedded<T> _embedded;
    private final List<Link> _links;
}
