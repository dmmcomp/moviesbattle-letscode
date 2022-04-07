package br.com.letscode.moviesbattle.controller;

import br.com.letscode.moviesbattle.DTO.GameStatusResponse;
import br.com.letscode.moviesbattle.Utils.UserSecutiryUtils;
import br.com.letscode.moviesbattle.entity.Game;
import br.com.letscode.moviesbattle.entity.Match;
import br.com.letscode.moviesbattle.entity.User;
import br.com.letscode.moviesbattle.service.GameService;
import br.com.letscode.moviesbattle.service.MatchService;
import br.com.letscode.moviesbattle.service.MovieService;
import br.com.letscode.moviesbattle.service.UserService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/match")
public class MatchController {

    private final MatchService matchService;
    private final UserService userService;
    private final GameService gameService;
    private final MovieService movieService;

    @Autowired
    public MatchController(MatchService matchService, UserService userService, GameService gameService, MovieService movieService) {
        this.matchService = matchService;
        this.userService = userService;
        this.gameService = gameService;
        this.movieService = movieService;
    }

    @PostMapping
    public Game createMatch(@RequestHeader("Authorization") String token){
        User user = userService.findByUsername(UserSecutiryUtils.getUsernameFromToken(token)).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário inválido(a)"));;
        if(matchService.getActiveMatchByUser(user).isPresent()) throw new ResponseStatusException(HttpStatus.CONFLICT, "O Usuário já possui uma partida em andamento");
        return matchService.initMatch(user);
    }

    @GetMapping("/newPair")
    public Game newGame(@RequestHeader("Authorization") String token){
        User user = userService.findByUsername(UserSecutiryUtils.getUsernameFromToken(token)).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário inválido(a)"));;
        Match activeMatch = matchService.getActiveMatchByUser(user).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario sem partida ativa, iniciar uma nova partida."));
        return gameService.createOrGetActiveGame(activeMatch);

    }

    @GetMapping("/status/{id}")
    public Match matchStatus(@RequestHeader("Authorization") String token, @PathVariable("id") Long id){
        String username = UserSecutiryUtils.getUsernameFromToken(token);
        Match match = matchService.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Partida não encontrada, favor verificar os parâmetros enviados"));
        if(!match.getUser().getUsername().equals(username)) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "A Partida não pertence ao usuário: " + username);
        return match;
    }

    @PostMapping("/responseGame/{id}/{movie}")
    public GameStatusResponse executeGame(@RequestHeader("Authorization") String token, @PathVariable("id") Long id, @PathVariable("movie") String movieId){
        String username = UserSecutiryUtils.getUsernameFromToken(token);
        Game game = gameService.getPendingGameById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rodada não encontrada ou finalizada, favor verificar os parâmetros enviados"));
        if(!game.getMatch().getUser().getUsername().equals(username)) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "A rodada não pertence ao usuário: " + username);
        movieService.getById(movieId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Filme inválido, favor verificar os parâmetros enviados."));

        game = gameService.setGameResult(movieId, game);
        matchService.updateMatchStatus(game);
        return new GameStatusResponse(game);
    }
}
