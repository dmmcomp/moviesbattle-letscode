package br.com.letscode.moviesbattle.repository;

import br.com.letscode.moviesbattle.constant.Status;
import br.com.letscode.moviesbattle.entity.Game;
import br.com.letscode.moviesbattle.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long > {
    @Query("SELECT COUNT(id) FROM Game g where (g.key = :key1 or g.key = :key2) AND g.match = :match")
    Integer checkMovieUse(String key1, String key2, Match match);

    Optional<Game> findFirstByResultAndMatch(Status result, Match match);
    Optional<Game> findFirstByIdAndResult(Long id, Status result);
}
