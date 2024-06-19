package com.example.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    private Integer stars;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pokemon_id")
    private Pokemon pokemon;
}
