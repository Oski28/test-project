package com.example.testproject.shared.hateoas_response;

import lombok.Data;
import java.util.Collection;

@Data
public class Embedded<T> {

    private final Collection<T> content;
    private final PageInfo page;
}
