package com.programmers.ticketing.controller;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageResult<T> {
    private final List<T> data;
    private final boolean hasNext;
    private final boolean hasPrev;
    private final int page;
    private final int totalPages;

    public PageResult(Page<T> data) {
        this.data = data.getContent();
        this.hasNext = data.hasNext();
        this.hasPrev = data.hasPrevious();
        this.page = data.getNumber();
        this.totalPages = data.getTotalPages();
    }
}
