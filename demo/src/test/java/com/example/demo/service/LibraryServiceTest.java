package com.example.demo.service;

import com.example.demo.dto.BookDto;
import com.example.demo.entity.Book;
import com.example.demo.entity.Student;
import com.example.demo.exception.NLFNotFoundException;
import com.example.demo.mapper.BookMapper;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.impl.LibraryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class LibraryServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private LibraryServiceImpl libraryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIssueBook() {
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

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        libraryService.issueBook(1L, 1L);

        Assertions.assertEquals(2L, book.getAvailableCopies());
        Assertions.assertTrue(student.getBorrowedBooks().contains(book));

        Mockito.verify(bookRepository, Mockito.times(1)).save(book);
        Mockito.verify(studentRepository, Mockito.times(1)).save(student);
    }

    @Test
    void testIssueBookBookNotAvailable() {
        Book book = Book.builder()
                .id(2L)
                .title("B")
                .availableCopies(0L)
                .totalCopies(3L)
                .students(new HashSet<>())
                .build();

        Student student = Student.builder()
                .id(2L)
                .fullName("S")
                .borrowedBooks(new HashSet<>())
                .build();

        Mockito.when(bookRepository.findById(2L)).thenReturn(Optional.of(book));
        Mockito.when(studentRepository.findById(2L)).thenReturn(Optional.of(student));

        Assertions.assertThrows(NLFNotFoundException.class, () -> libraryService.issueBook(2L, 2L));

        Mockito.verify(bookRepository, Mockito.never()).save(book);
        Mockito.verify(studentRepository, Mockito.never()).save(student);
    }

    @Test
    void testGetBorrowedBooks() {
        Book book = Book.builder()
                .id(1L)
                .title("B")
                .availableCopies(3L)
                .totalCopies(3L)
                .students(new HashSet<>())
                .build();

        BookDto bookDto = BookDto.builder()
                .id(1L)
                .title("B")
                .availableCopies(3L)
                .totalCopies(3L)
                .build();

        Mockito.when(bookRepository.findBorrowedBooks()).thenReturn(Collections.singletonList(book));
        Mockito.when(bookMapper.toDto(book)).thenReturn(bookDto);

        List<BookDto> borrowedBooks = libraryService.getBorrowedBooks();

        Assertions.assertEquals(1, borrowedBooks.size());
        Assertions.assertEquals(bookDto, borrowedBooks.get(0));
    }
}
