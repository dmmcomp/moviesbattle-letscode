package br.com.letscode.moviesbattle.repository;

import br.com.letscode.moviesbattle.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, String > {

    List<Movie> findMoviesByIdNotIn(List<String> ids);
}
