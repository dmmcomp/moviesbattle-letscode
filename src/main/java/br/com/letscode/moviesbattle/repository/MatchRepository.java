package br.com.letscode.moviesbattle.repository;

import br.com.letscode.moviesbattle.constant.Status;
import br.com.letscode.moviesbattle.entity.Match;
import br.com.letscode.moviesbattle.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchRepository extends JpaRepository<Match, Long > {
    Optional<Match> findFirstByStatusAndUser(Status status, User user);
}
