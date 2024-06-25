package com.example.repository;

import com.example.models.Pokemon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PokemonRepositoryTests {


    @Autowired
    private PokemonRepository pokemonRepository;


    @Test
    @DisplayName("Save Pokemon")
    public void PokemonRepository_SaveAll_ReturnSavedPokemon() {
        //Arrange
        Pokemon pokemon = Pokemon.builder().name("Pikachu").type("Electric").build();

        //Act
        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        //Assert
        assertThat(savedPokemon).isNotNull();
        assertThat(savedPokemon.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Find All Pokemon")
    public void PokemonRepository_FindAll_ReturnOneMorePokemon() {
        Pokemon pokemon = Pokemon.builder().name("Pikachu").type("Electric").build();
        Pokemon pokemon2 = Pokemon.builder().name("Pikachu").type("Electric").build();

        pokemonRepository.saveAll(List.of(pokemon, pokemon2));

        List<Pokemon> pokemonList = pokemonRepository.findAll();

        assertThat(pokemonList).isNotNull();
        assertThat(pokemonList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Find Pokemon By Id")
    public void PokemonRepository_FindById_ReturnPokemon() {
        Pokemon pokemon = Pokemon.builder().name("Pikachu").type("Electric").build();

        pokemonRepository.save(pokemon);
        Pokemon returnedPokemon = pokemonRepository.findById(pokemon.getId()).get();

        assertThat(returnedPokemon).isNotNull();
    }

    @Test
    @DisplayName("Find Pokemon By Name")
    public void PokemonRepository_FindByName_ReturnPokemon() {
        Pokemon pokemon = Pokemon.builder().name("Pikachu").type("Electric").build();

        pokemonRepository.save(pokemon);
        Pokemon returnedPokemon = pokemonRepository.findByName("Pikachu").get();

        assertThat(returnedPokemon).isNotNull();
    }

    @Test
    @DisplayName("Update Pokemon")
    public void PokemonRepository_UpdatePokemon_ReturnUpdatedPokemon() {
        Pokemon pokemon = Pokemon.builder().name("Pikachu").type("Electric").build();
        pokemonRepository.save(pokemon);
        Pokemon updatedPokemon = pokemonRepository.findByName("Pikachu").get();
        updatedPokemon.setName("Charmander");
        updatedPokemon.setType("Fire");
        pokemonRepository.save(updatedPokemon);

        assertThat(updatedPokemon.getName()).isEqualTo("Charmander");
        assertThat(updatedPokemon.getType()).isEqualTo("Fire");
    }

    @Test
    @DisplayName("Delete Pokemon By Id")
    public void PokemonRepository_DeleteById_ReturnPokemonIsEmpty() {
        Pokemon pokemon = Pokemon.builder().name("Pikachu").type("Electric").build();
        pokemonRepository.save(pokemon);
        pokemonRepository.deleteById(pokemon.getId());
        Optional<Pokemon> deletedPokemon = pokemonRepository.findById(pokemon.getId());
        assertThat(deletedPokemon).isEmpty();
    }


}
