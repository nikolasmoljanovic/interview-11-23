package com.example.demo.mapper;

import com.example.demo.dto.BookDto;
import com.example.demo.entity.Book;

public interface BookMapper {

    BookDto toDto(Book book);
}
