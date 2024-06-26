package com.example.service;

import com.example.dto.PokemonDto;
import com.example.dto.ReviewDto;
import com.example.models.Pokemon;
import com.example.models.Review;
import com.example.repository.PokemonRepository;
import com.example.repository.ReviewRepository;
import com.example.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTests {
    @Mock
    private PokemonRepository pokemonRepository;
    @Mock
    private ReviewRepository reviewRepository;
    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Pokemon pokemon;
    private PokemonDto pokemonDto;
    private Review review;
    private ReviewDto reviewDto;

    @BeforeEach
    public void init() {
        pokemon = Pokemon.builder().name("Pikachu").type("Electric").build();
        pokemonDto = PokemonDto.builder().name("Pikachu").type("Electric").build();
        review = Review.builder().title("title").content("content").stars(5).build();
        reviewDto = ReviewDto.builder().title("title").content("content").stars(5).build();
    }

    @Test
    @DisplayName("Create Review")
    public void ReviewService_CreateReview_ReturnReview() {
        when(pokemonRepository.findById(pokemon.getId())).thenReturn(Optional.ofNullable(pokemon));
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);
        review.setPokemon(pokemon);

        ReviewDto createdReview = reviewService.createReview(pokemon.getId(), reviewDto);
        assertThat(createdReview).isNotNull();
    }

    @Test
    @DisplayName("Find Reviews By PokemonId")
    public void ReviewService_GetReviewsByPokemonId_ReturnReviews() {
        int pokemonId = 1;
        when(reviewRepository.findByPokemonId(pokemonId)).thenReturn(Optional.ofNullable(Arrays.asList(review)));

        List<ReviewDto> reviewDtoList = reviewService.getReviewsByPokemonId(pokemonId);

        assertThat(reviewDtoList).isNotNull();
    }

    @Test
    @DisplayName("Test Review and Pokemon With Id")
    public void ReviewService_TestReviewAndPokemonById_ReturnReview() {
        int reviewId = 1;
        int pokemonId = 1;
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.ofNullable(review));
        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.ofNullable(pokemon));
        review.setPokemon(pokemon);

        ReviewDto testReviewDto = reviewService.testReviewAndPokemonWithId(pokemonId, reviewId);

        assertThat(testReviewDto).isNotNull();
    }

    @Test
    @DisplayName("Update Review")
    public void ReviewService_UpdateReview_ReturnUpdatedReview() {
        int reviewId = 1;
        int pokemonId = 1;
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.ofNullable(review));
        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.ofNullable(pokemon));
        review.setPokemon(pokemon);
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        ReviewDto updatedReview = reviewService.updateReview(pokemonId, reviewId, reviewDto);

        assertThat(updatedReview).isNotNull();
    }

    @Test
    @DisplayName("Delete Review")
    public void ReviewService_DeleteReview_ReturnEmpty() {
        int reviewId = 1;
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.ofNullable(review));

        assertAll(() -> reviewService.deleteReview(reviewId));
    }


}
