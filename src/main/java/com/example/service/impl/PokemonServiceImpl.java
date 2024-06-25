package com.example.service.impl;

import com.example.dto.PokemonDto;
import com.example.dto.PokemonResponse;
import com.example.exceptions.PokemonNotFoundException;
import com.example.models.Pokemon;
import com.example.repository.PokemonRepository;
import com.example.service.inter.PokemonService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.stream.Collectors;

@Service
public class PokemonServiceImpl implements PokemonService {
    @Autowired
    private PokemonRepository pokemonRepository;

    @Override
    public PokemonDto createPokemon(PokemonDto pokemonDto) {
        Pokemon pokemon = pokemonRepository.save(mapToEntity(pokemonDto));
        pokemonDto.setId(pokemon.getId());
        return pokemonDto;
    }

    @Override
    public PokemonResponse getAllPokemons(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Pokemon> pokemons = pokemonRepository.findAll(pageable);
        List<Pokemon> listOfPokemon = pokemons.getContent();
        List<PokemonDto> content = listOfPokemon.stream().map(pokemon -> mapToDto(pokemon)).collect(Collectors.toList());

        PokemonResponse pokemonResponse = new PokemonResponse();
        pokemonResponse.setContent(content);
        pokemonResponse.setPageNo(pageNo);
        pokemonResponse.setPageSize(pageSize);
        pokemonResponse.setTotalElements(pokemons.getTotalElements());
        pokemonResponse.setTotalPages(pokemons.getTotalPages());
        pokemonResponse.setLast(pokemons.isLast());

        return pokemonResponse;

    }

    @Override
    public PokemonDto getPokemonById(Integer id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundException("Pokemon could not be found"));
        return mapToDto(pokemon);
    }

    @Override
    public PokemonDto updatePokemonById(PokemonDto pokemonDto, Integer id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundException("Pokemon could not be updated"));
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());
        pokemonRepository.save(pokemon);
        return mapToDto(pokemon);
    }

    @Override
    public void deletePokemonById(Integer id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundException("Pokemon could not be deleted"));
        pokemonRepository.delete(pokemon);
    }

    private PokemonDto mapToDto(Pokemon pokemon) {
        PokemonDto pokemonDto = new PokemonDto();
        pokemonDto.setId(pokemon.getId());
        pokemonDto.setName(pokemon.getName());
        pokemonDto.setType(pokemon.getType());
        return pokemonDto;
    }

    private Pokemon mapToEntity(PokemonDto pokemonDto) {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());
        return pokemon;
    }

}
