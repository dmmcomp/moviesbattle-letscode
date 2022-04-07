package br.com.letscode.moviesbattle.service;

import br.com.letscode.moviesbattle.security.data.UserDetailData;
import br.com.letscode.moviesbattle.entity.User;
import br.com.letscode.moviesbattle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findFirstByUsername(username);
    }

    public User save(User user){
        Optional<User> existingUser = findByUsername(user.getUsername());
        if(existingUser.isPresent()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já cadastrado!");
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = findByUsername(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return new UserDetailData(user);
    }
}
