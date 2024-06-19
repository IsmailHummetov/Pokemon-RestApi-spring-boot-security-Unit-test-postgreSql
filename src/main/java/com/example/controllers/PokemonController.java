package com.example.controllers;

import com.example.dto.PokemonDto;
import com.example.dto.PokemonResponse;
import com.example.service.inter.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class PokemonController {
    @Autowired
    private PokemonService pokemonService;

    @GetMapping("pokemon")
    public ResponseEntity<PokemonResponse> getPokemons(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize) {
        return ResponseEntity.ok(pokemonService.getAllPokemons(pageNo, pageSize));
    }

    @GetMapping("pokemon/{id}")
    public ResponseEntity<PokemonDto> pokemonDetails(@PathVariable("id") Integer pokemonId) {
        return ResponseEntity.ok(pokemonService.getPokemonById(pokemonId));
    }

    @PostMapping("pokemon/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PokemonDto> createPokemon(@RequestBody PokemonDto pokemonDto) {
        return new ResponseEntity<>(pokemonService.createPokemon(pokemonDto), HttpStatus.CREATED);
    }

    @PutMapping("pokemon/{id}/update")
    public ResponseEntity<PokemonDto> updatePokemon(@RequestBody PokemonDto pokemonDto,
                                                    @PathVariable("id") Integer pokemonId) {
        return ResponseEntity.ok(pokemonService.updatePokemonById(pokemonDto, pokemonId));
    }

    @DeleteMapping("pokemon/{id}/delete")
    public ResponseEntity<String> deletePokemon(@PathVariable("id") Integer pokemonId) {
        pokemonService.deletePokemonById(pokemonId);
        return ResponseEntity.ok("Pokemon was deleted");
    }
}
