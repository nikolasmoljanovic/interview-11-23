package com.example.demo.exceptionHandler;

import com.example.demo.exception.BookAlreadyIssuedException;
import com.example.demo.exception.BookNotIssuedException;
import com.example.demo.exception.NLFNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NLFNotFoundException.class)
    public ResponseEntity<Object> handleBookNotFoundException(NLFNotFoundException ex) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("error", "NotFound");
        responseBody.put("message", ex.getMessage());

        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookAlreadyIssuedException.class)
    public ResponseEntity<Object> handleBookAlreadyIssuedException(BookAlreadyIssuedException ex) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("error", "BookAlreadyIssued");
        responseBody.put("message", ex.getMessage());

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookNotIssuedException.class)
    public ResponseEntity<Object> handleBookNotIssuedException(BookNotIssuedException ex) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("error", "BookNotIssued");
        responseBody.put("message", ex.getMessage());

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }
}
