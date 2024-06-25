package com.example.service.impl;

import com.example.dto.ReviewDto;
import com.example.exceptions.PokemonNotFoundException;
import com.example.exceptions.ReviewNotFoundException;
import com.example.models.Pokemon;
import com.example.models.Review;
import com.example.repository.PokemonRepository;
import com.example.repository.ReviewRepository;
import com.example.service.inter.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private PokemonRepository pokemonRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public ReviewDto createReview(Integer pokemonId, ReviewDto reviewDto) {
        Review review = mapToEntity(reviewDto);
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() ->
                new PokemonNotFoundException("Pokemon with associated review could not be found"));
        review.setPokemon(pokemon);
        Review newReview = reviewRepository.save(review);
        return mapToDto(newReview);
    }

    @Override
    public List<ReviewDto> getReviewsByPokemonId(Integer pokemonId) {
        List<Review> reviews = reviewRepository.findByPokemonId(pokemonId).orElseThrow(() ->
                        new PokemonNotFoundException("Reviews with associated pokemon could not be found"));
        return reviews.stream().map(review -> mapToDto(review)).collect(Collectors.toList());
    }

    @Override
    public ReviewDto testReviewAndPokemonWithId(Integer pokemonId, Integer reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
                new ReviewNotFoundException("Review could not be found"));
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() ->
                new PokemonNotFoundException("Pokemon could not be found"));
        if (review.getPokemon().getId() != pokemon.getId())
            throw new ReviewNotFoundException("This review does not belong to this pokemon");
        return mapToDto(review);
    }

    @Override
    public ReviewDto updateReview(Integer pokemonId, Integer reviewId, ReviewDto reviewDto) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
                new ReviewNotFoundException("Review could not be found"));
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() ->
                new PokemonNotFoundException("Pokemon could not be found"));
        if (review.getPokemon().getId() != pokemon.getId())
            throw new ReviewNotFoundException("This review does not belong to this pokemon");
        review.setTitle(reviewDto.getTitle());
        review.setStars(reviewDto.getStars());
        review.setContent(reviewDto.getContent());
        reviewRepository.save(review);
        return mapToDto(review);
    }

    @Override
    public void deleteReview(Integer reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
                new ReviewNotFoundException("Review could not be found"));
        reviewRepository.delete(review);
    }

    private ReviewDto mapToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setContent(review.getContent());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setStars(review.getStars());
        return reviewDto;
    }

    private Review mapToEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setTitle(reviewDto.getTitle());
        review.setStars(reviewDto.getStars());
        review.setContent(reviewDto.getContent());
        return review;
    }
}
