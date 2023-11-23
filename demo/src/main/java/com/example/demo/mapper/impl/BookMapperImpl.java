package com.example.demo.mapper.impl;

import com.example.demo.dto.BookDto;
import com.example.demo.entity.Book;
import com.example.demo.mapper.BookMapper;
import org.springframework.stereotype.Service;

@Service
public class BookMapperImpl implements BookMapper {
    @Override
    public BookDto toDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .totalCopies(book.getTotalCopies())
                .availableCopies(book.getAvailableCopies())
                .build();
    }
}
