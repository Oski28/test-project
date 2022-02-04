package com.example.testproject.shared.hateoas_response;

import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
public class PageInfo {

    private final Pageable pageable;
    private final Sort sort;
    private final boolean last;
    private final int totalPages;
    private final long totalElements;
    private final int size;
    private final int number;
    private final boolean first;
    private final int numberOfElements;
    private final boolean empty;

    public PageInfo(Page page) {
        this.pageable = page.getPageable();
        this.sort = page.getSort();
        this.last = page.isLast();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.size = page.getSize();
        this.number = page.getNumber();
        this.first = page.isFirst();
        this.numberOfElements = page.getNumberOfElements();
        this.empty = page.isEmpty();
    }
}
