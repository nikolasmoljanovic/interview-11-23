package com.example.demo.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
    private Long id;
    private String title;
    private Long totalCopies;
    private Long availableCopies;
}