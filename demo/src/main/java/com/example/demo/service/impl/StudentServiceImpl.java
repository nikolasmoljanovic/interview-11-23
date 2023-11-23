package com.example.demo.service.impl;

import com.example.demo.entity.Book;
import com.example.demo.entity.Student;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public void save(Student student) {
        studentRepository.save(student);
    }
}
