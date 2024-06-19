package com.example.controllers;

import com.example.dto.ReviewDto;
import com.example.service.inter.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping("review/{pokemonId}/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReviewDto> createReview(@PathVariable("pokemonId") Integer pokemonId,
                                                  @RequestBody ReviewDto reviewDto) {
        return new ResponseEntity<>(reviewService.createReview(pokemonId,reviewDto),HttpStatus.CREATED);
    }

    @GetMapping("pokemon/{pokemonId}/review")
    public ResponseEntity<List<ReviewDto>> getReviewsByPokemonId(@PathVariable("pokemonId") Integer pokemonId){
        return ResponseEntity.ok(reviewService.getReviewsByPokemonId(pokemonId));
    }

    @GetMapping("pokemon/{pokemonId}/review/{reviewId}")
    public ResponseEntity<ReviewDto> testReviewAndPokemonWithId(@PathVariable("pokemonId")Integer pokemonId,
                                                                @PathVariable("reviewId")Integer reviewId){
        return ResponseEntity.ok(reviewService.testReviewAndPokemonWithId(pokemonId,reviewId));
    }

    @PutMapping("pokemon/{pokemonId}/review/{reviewId}/update")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable("pokemonId")Integer pokemonId,
                                                  @PathVariable("reviewId")Integer reviewId,
                                                  @RequestBody ReviewDto reviewDto){
        return ResponseEntity.ok(reviewService.updateReview(pokemonId, reviewId, reviewDto));
    }

    @DeleteMapping("review/{reviewId}/delete")
    public ResponseEntity<String> deleteReview(@PathVariable("reviewId")Integer reviewId){
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>("Review was deleted",HttpStatus.OK);
    }
}
