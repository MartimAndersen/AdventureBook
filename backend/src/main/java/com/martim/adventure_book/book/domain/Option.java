package com.martim.adventure_book.book.domain;

public record Option(String description, Integer gotoId, Consequence consequence) {}