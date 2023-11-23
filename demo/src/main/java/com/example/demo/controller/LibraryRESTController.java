package com.example.demo.controller;

import com.example.demo.dto.BookDto;
import com.example.demo.service.LibraryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
@AllArgsConstructor
public class LibraryRESTController {

    private final LibraryService libraryService;

    @PostMapping("/issue/{bookId}/{studentId}")
    public ResponseEntity<Void> issueBook(@PathVariable Long bookId, @PathVariable Long studentId) {
        libraryService.issueBook(bookId, studentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/return/{bookId}/{studentId}")
    public ResponseEntity<Void> returnBook(@PathVariable Long bookId, @PathVariable Long studentId) {
        libraryService.returnBook(bookId, studentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/borrowed-books")
    public ResponseEntity<List<BookDto>> getBorrowedBooks() {
        List<BookDto> borrowedBooks = libraryService.getBorrowedBooks();
        return ResponseEntity.ok(borrowedBooks);
    }
}
