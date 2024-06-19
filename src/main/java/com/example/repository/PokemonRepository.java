package com.example.repository;

import com.example.models.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon,Integer> {
}
