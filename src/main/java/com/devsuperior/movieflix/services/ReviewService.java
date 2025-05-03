package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final MovieRepository movieRepository;

    private final AuthService authService;

    public ReviewService(ReviewRepository reviewRepository, MovieRepository movieRepository, AuthService authService) {
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
        this.authService = authService;
    }

    @Transactional
    public ReviewDTO insert(ReviewDTO reviewDTO) {
        Movie movie = movieRepository.findById(reviewDTO.getMovieId()).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        Review review = new Review();
        review.setText(reviewDTO.getText());
        review.setMovie(movie);
        User user = authService.authenticated();
        review.setUser(user);
        review = reviewRepository.save(review);
        return new ReviewDTO(review);
    }
}
