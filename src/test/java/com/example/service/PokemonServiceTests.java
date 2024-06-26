package com.example.service;

import com.example.dto.PokemonDto;
import com.example.dto.PokemonResponse;
import com.example.models.Pokemon;
import com.example.repository.PokemonRepository;
import com.example.service.impl.PokemonServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceTests {
    @Mock
    private PokemonRepository pokemonRepository;
    @InjectMocks
    private PokemonServiceImpl pokemonService;

    @Test
    @DisplayName("Create Pokemon")
    public void PokemonService_CreatePokemon_ReturnPokemon() {
        Pokemon pokemon = Pokemon.builder().name("Pikachu").type("Electric").build();
        PokemonDto pokemonDto = PokemonDto.builder().name("Pikachu").type("Electrik").build();

        when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);

        PokemonDto savedPokemon = pokemonService.createPokemon(pokemonDto);

        assertThat(savedPokemon).isNotNull();
    }

    @Test
    @DisplayName("Get All Pokemon")
    public void PokemonService_GetAll_ReturnAllPokemon() {
        Page<Pokemon> pokemonPage = Mockito.mock(Page.class);

        when(pokemonRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pokemonPage);

        PokemonResponse pokemonResponse = pokemonService.getAllPokemons(1, 10);

        assertThat(pokemonResponse).isNotNull();
    }

    @Test
    @DisplayName(("Get Pokemon By Id"))
    public void PokemonService_GetPokemonById_ReturnPokemon() {
        Pokemon pokemon = Pokemon.builder().name("Pikachu")
                .type("Electric").build();
        int pokemonId=1;

        when(pokemonRepository.findById(1)).thenReturn(Optional.ofNullable(pokemon));

        PokemonDto pokemonDto = pokemonService.getPokemonById(pokemonId);

        assertThat(pokemonDto).isNotNull();
    }

    @Test
    @DisplayName("Update Pokemon")
    public void PokemonService_UpdatePokemon_ReturnUpdatedPokemon() {
        Pokemon pokemon = Pokemon.builder().name("Pikachu")
                .type("Electric").build();
        PokemonDto pokemonDto = PokemonDto.builder().name("Pikachu").type("Electrik").build();
        int pokemonId=1;

        when(pokemonRepository.findById(1)).thenReturn(Optional.ofNullable(pokemon));
        when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);

        PokemonDto updatedPokemon = pokemonService.updatePokemonById(pokemonDto, pokemonId);

        assertThat(updatedPokemon).isNotNull();
    }

    @Test
    @DisplayName("Delete Pokemon")
    public void PokemonService_DeletePokemon_ReturnPokemonIsEmpty() {
        Pokemon pokemon = Pokemon.builder().name("Pikachu")
                .type("Electric").build();
        int pokemonId=1;

        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.ofNullable(pokemon));
        assertAll(() -> pokemonService.deletePokemonById(pokemonId));
    }
}
