package br.com.letscode.moviesbattle.service;

import br.com.letscode.moviesbattle.entity.Match;
import br.com.letscode.moviesbattle.entity.Movie;
import br.com.letscode.moviesbattle.repository.GameRepository;
import br.com.letscode.moviesbattle.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    GameRepository gameRepository;

    public Movie save(Movie movie){
        return movieRepository.save(movie);
    }

    public Movie getRandomMovie(){
        List<Movie> movies = movieRepository.findAll();
        Collections.shuffle(movies);
        return movies.get(0);
    }

    public List<Movie> getReorderedMovies(){
        List<Movie> movies = movieRepository.findAll();
        Collections.shuffle(movies);
        return movies;
    }

    public Optional<Movie> getById(String movieId){
       return movieRepository.findById(movieId);
    }

    public Movie getNotUsedMovie(Movie movie, Match match) throws Exception{
            List<Movie> movies = getReorderedMovies();
        return getValidMovie(movie, match, movies);
    }

    public Movie getBestMovie(Movie movie1, Movie movie2){
        return (movie1.getScore() > movie2.getScore() ? movie1 : movie2);
    }

    public boolean isValidMovie(Movie movie, Movie pairMovie, Match match){
        boolean isValid = true;
        if(movie.getId().equals(pairMovie.getId()) || movie.getScore().equals(pairMovie.getScore())) isValid = false;

        if(gameRepository.checkMovieUse(getKey(movie, pairMovie), getKey(pairMovie, movie), match) > 0){
            isValid = false;
        }
        return isValid;
    }
    private Movie getValidMovie(Movie movie, Match match, List<Movie> movies) throws Exception {
        Movie pairMovie;
        for (Movie movie1 : movies) {
            pairMovie = movie1;
            if (isValidMovie(movie, pairMovie, match)) return pairMovie;
        }
        throw new Exception();
    }

    public String getKey(Movie movie1, Movie movie2){
        return movie1.getId() + "_" + movie2.getId();
    }
}
