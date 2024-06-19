package com.example.dto;

import com.example.models.Pokemon;
import lombok.Data;

@Data
public class PokemonDto {
    private Integer id;
    private String name;
    private String type;
}
