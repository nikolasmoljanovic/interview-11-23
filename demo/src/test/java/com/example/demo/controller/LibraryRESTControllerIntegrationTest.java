package com.example.demo.controller;

import com.example.demo.dto.BookDto;
import com.example.demo.entity.Book;
import com.example.demo.entity.Student;
import com.example.demo.service.BookService;
import com.example.demo.service.LibraryService;
import com.example.demo.service.StudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@SpringBootTest(properties = "spring.liquibase.enabled=false")
public class LibraryRESTControllerIntegrationTest {

    @Autowired
    private LibraryRESTController libraryController;

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private BookService bookService;

    @Autowired
    private StudentService studentService;

    @Test
    public void testIssueAndReturnBook() {

        Book book = Book.builder()
                .id(1L)
                .title("B")
                .availableCopies(3L)
                .totalCopies(3L)
                .students(new HashSet<>())
                .build();

        Student student = Student.builder()
                .id(1L)
                .fullName("S")
                .borrowedBooks(new HashSet<>())
                .build();

        bookService.save(book);
        studentService.save(student);

        ResponseEntity<Void> issueResponse = libraryController.issueBook(1L, 1L);
        ResponseEntity<Void> returnResponse = libraryController.returnBook(1L, 1L);

        Assertions.assertEquals(issueResponse.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(returnResponse.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testGetBorrowedBooks() {
        Book book = Book.builder()
                .id(1L).title("B")
                .availableCopies(3L)
                .totalCopies(3L)
                .build();
        bookService.save(book);

        Student student = Student.builder()
                .id(1L).fullName("Nikola Smoljanovic")
                .build();
        studentService.save(student);

        libraryService.issueBook(book.getId(), student.getId());

        ResponseEntity<List<BookDto>> responseEntity = libraryController.getBorrowedBooks();

        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(Objects.requireNonNull(responseEntity.getBody()).get(0).getTitle(), "B");
    }
}
