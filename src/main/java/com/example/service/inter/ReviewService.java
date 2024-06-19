package com.example.service.inter;

import com.example.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    ReviewDto createReview(Integer pokemonId, ReviewDto reviewDto);

    List<ReviewDto> getReviewsByPokemonId(Integer pokemonId);

    ReviewDto testReviewAndPokemonWithId(Integer pokemonId, Integer reviewId);

    ReviewDto updateReview(Integer pokemonId, Integer reviewId, ReviewDto reviewDto);

    void deleteReview(Integer reviewId);
}
