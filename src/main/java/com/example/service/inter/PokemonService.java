package com.example.service.inter;

import com.example.dto.PokemonDto;
import com.example.dto.PokemonResponse;

import java.util.List;

public interface PokemonService {
    PokemonDto createPokemon(PokemonDto pokemonDto);

    PokemonResponse getAllPokemons(int pageNo, int pageSize);

    PokemonDto getPokemonById(Integer id);

    PokemonDto updatePokemonById(PokemonDto pokemonDto, Integer id);

    void deletePokemonById(Integer id);

}
