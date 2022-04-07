package br.com.letscode.moviesbattle.repository;

import br.com.letscode.moviesbattle.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long > {
    Optional<User> findFirstByUsername(String username);
}
