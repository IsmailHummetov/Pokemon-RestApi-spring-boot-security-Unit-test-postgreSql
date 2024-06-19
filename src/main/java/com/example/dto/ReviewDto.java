package com.example.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private Integer id;
    private String title;
    private String content;
    private Integer stars;
}
