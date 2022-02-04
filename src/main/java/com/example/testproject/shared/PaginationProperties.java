package com.example.testproject.shared;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("app.pagination")
public class PaginationProperties {

    private final int PAGE = 0;
    private final int SIZE = 20;
    private final String COLUMN = "id";
    private final String DIRECTION = "ASC";
    private final String FILTER = "";
}
