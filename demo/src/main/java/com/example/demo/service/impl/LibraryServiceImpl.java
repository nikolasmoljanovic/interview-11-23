package com.example.demo.service.impl;

import com.example.demo.dto.BookDto;
import com.example.demo.entity.Book;
import com.example.demo.entity.Student;
import com.example.demo.exception.BookAlreadyIssuedException;
import com.example.demo.exception.BookNotIssuedException;
import com.example.demo.exception.NLFNotFoundException;
import com.example.demo.mapper.BookMapper;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.LibraryService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;

    private final StudentRepository studentRepository;

    private final BookMapper bookMapper;

    public void issueBook(Long bookId, Long studentId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NLFNotFoundException("Book not found"));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new NLFNotFoundException("Student not found"));

        // check if the student already has the book
        if (student.getBorrowedBooks().contains(book)) {
            throw new BookAlreadyIssuedException("Student already has the book with id: " + bookId);
        }

        // check if there are available copies of the book
        if (book.getAvailableCopies() > 0) {
            student.getBorrowedBooks().add(book);
            book.getStudents().add(student);
            studentRepository.save(student);

            book.setAvailableCopies(book.getAvailableCopies() - 1);
            bookRepository.save(book);
        } else {
            throw new NLFNotFoundException("No copies of the book found");
        }
    }

    public void returnBook(Long bookId, Long studentId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NLFNotFoundException("Book not found"));

        Student student = studentRepository.findById(studentId).orElseThrow(() -> new NLFNotFoundException("Student not found"));

        // check if the student borrowed the book
        if (book.getStudents().contains(student)) {
            student.getBorrowedBooks().remove(book);

            book.getStudents().remove(student);

            book.setAvailableCopies(book.getAvailableCopies() + 1);

            studentRepository.save(student);
            bookRepository.save(book);
        } else {
            throw new BookNotIssuedException("Student did not borrow the specified book");
        }
    }

    public List<BookDto> getBorrowedBooks() {
        List<Book> borrowedBooks = bookRepository.findBorrowedBooks();
        return borrowedBooks.stream().map(bookMapper::toDto).collect(Collectors.toList());
    }
}
