package com.example.dto;

import com.example.models.Pokemon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PokemonDto {
    private Integer id;
    private String name;
    private String type;
}
