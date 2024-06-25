package com.example.repository;

import com.example.models.Pokemon;
import com.example.models.Review;
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
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    @DisplayName("Save Review")
    public void ReviewRepository_SaveAll_ReturnSavedReview() {
        Pokemon pokemon = Pokemon.builder().name("Pikachu").type("Electric").build();
        pokemonRepository.save(pokemon);
        Review review = Review.builder().title("title").content("content").stars(5).pokemon(pokemon).build();

        reviewRepository.save(review);

        assertThat(review).isNotNull();
        assertThat(review.getId()).isGreaterThan(0);

    }

    @Test
    @DisplayName("Find All")
    public void ReviewRepository_FindAll_ReturnOneMoreReviews() {
        Pokemon pokemon = Pokemon.builder().name("Pikachu").type("Electric").build();
        pokemonRepository.save(pokemon);

        Review review = Review.builder().title("title").content("content").stars(5).pokemon(pokemon).build();
        Review review2 = Review.builder().title("title").content("content").stars(5).pokemon(pokemon).build();

        reviewRepository.saveAll(List.of(review, review2));

        List<Review> reviewList = reviewRepository.findAll();

        assertThat(reviewList.size()).isEqualTo(2);
        assertThat(reviewList).isNotNull();

    }

    @Test
    @DisplayName("Find Review By Id")
    public void ReviewRepository_FindById_ReturnReview() {
        Review review = Review.builder().title("title").content("content").stars(5).build();

        reviewRepository.save(review);

        Review returnedReview = reviewRepository.findById(review.getId()).get();

        assertThat(returnedReview).isNotNull();
    }

    @Test
    @DisplayName("Find Review By Pokemon Id")
    public void ReviewRepository_FindByPokemonId_ReturnReview() {
        Pokemon pokemon = Pokemon.builder().name("Pikachu").type("Electric").build();
        pokemonRepository.save(pokemon);

        Review review = Review.builder().title("title").content("content").stars(5).pokemon(pokemon).build();

        reviewRepository.save(review);

        List<Review> returnedReview = reviewRepository.findByPokemonId(review.getPokemon().getId()).get();

        assertThat(returnedReview.size()).isGreaterThan(0);
        assertThat(returnedReview).isNotNull();
    }

    @Test
    @DisplayName("Update Review")
    public void ReviewRepository_UpdateReview_ReturnUpdatedReview() {
        Pokemon pokemon = Pokemon.builder().name("Pikachu").type("Electric").build();
        pokemonRepository.save(pokemon);
        Review review = Review.builder().title("title").content("content").stars(5).pokemon(pokemon).build();
        reviewRepository.save(review);

        Review updatedReview = reviewRepository.findById(review.getId()).get();

        updatedReview.getPokemon().setName("Charmander");
        updatedReview.getPokemon().setType("Fire");
        updatedReview.setContent("Content");

        reviewRepository.save(updatedReview);

        assertThat(updatedReview).isNotNull();
    }

    @Test
    @DisplayName("Delete Review")
    public void ReviewRepository_DeleteById_ReturnReviewIsEmpty() {
        Pokemon pokemon = Pokemon.builder().name("Pikachu").type("Electric").build();
        pokemonRepository.save(pokemon);
        Review review = Review.builder().title("title").content("content").stars(5).pokemon(pokemon).build();
        reviewRepository.save(review);

        reviewRepository.deleteById(review.getId());
        Optional<Review> deletedReview = reviewRepository.findById(review.getId());

        assertThat(deletedReview).isEmpty();
    }


}
