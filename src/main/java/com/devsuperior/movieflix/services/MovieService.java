package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<MovieCardDTO> findAll() {
        return movieRepository.findAll().stream().map(MovieCardDTO::new).toList();
    }

    public MovieDetailsDTO findById(Long id) {
        return movieRepository.findById(id).map(MovieDetailsDTO::new).orElseThrow(() -> new ResourceNotFoundException("Movie not found!"));
    }

    public Page<MovieCardDTO> searchMovies(Long genreId, Pageable pageable) {
        Page<Movie> result = movieRepository.searchMovies(genreId, pageable);
        return result.map(MovieCardDTO::new);
    }
}
