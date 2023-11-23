package com.example.demo.service;

import com.example.demo.dto.BookDto;
import com.example.demo.entity.Book;

import java.util.List;

public interface LibraryService {

    void issueBook(Long bookId, Long studentId);

    void returnBook(Long bookId, Long studentId);

    List<BookDto> getBorrowedBooks();
}
