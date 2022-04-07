package br.com.letscode.moviesbattle.service;

import br.com.letscode.moviesbattle.utils.constant.Status;
import br.com.letscode.moviesbattle.entity.Game;
import br.com.letscode.moviesbattle.entity.Match;
import br.com.letscode.moviesbattle.entity.Movie;
import br.com.letscode.moviesbattle.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class GameService {

    private final MovieService movieService;

    private final GameRepository gameRepository;

    @Autowired
    public GameService(MovieService movieService, GameRepository gameRepository) {
        this.movieService = movieService;
        this.gameRepository = gameRepository;
    }

    public Optional<Game> getPendingGameById(Long id){
        return gameRepository.findFirstByIdAndResult(id, Status.PENDING);
    }

    public Game setGameResult(String movieId, Game game){
        Movie bestMovie = movieService.getBestMovie(game.getMovie1(),game.getMovie2());
        if(!movieId.equals(game.getMovie1().getId()) && !movieId.equals(game.getMovie2().getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Filme Inválido");
        if(bestMovie.getId().equals(movieId)){
            game.setResult(Status.WIN);
        }else{
            game.setResult(Status.LOSE);
        }
        return save(game);
    }

    public Game createOrGetActiveGame(Match match){
        Optional<Game> activeGame = getActiveGameIfExists(match);
        return activeGame.orElseGet(() -> getNewGame(match));
    }

    private Game getNewGame(Match match) {
        Game game = new Game();
        boolean control = false;
        do{
            try{
                Movie movie1 = movieService.getRandomMovie();
                Movie movie2 = movieService.getNotUsedMovie(movie1, match);
                game.setMovie1(movie1);
                game.setMovie2(movie2);
                game.setMatch(match);
                game.setKey(movieService.getKey(movie1,movie2));
                game.setResult(Status.PENDING);
                control = true;
                game = gameRepository.save(game);
            }catch (Exception e){
                System.out.println("Falha na definição de filmes");
            }
        }while(!control);

        return game;
    }

    private Game save(Game game){
        return gameRepository.save(game);
    }

    private Optional<Game> getActiveGameIfExists(Match match){
        return gameRepository.findFirstByResultAndMatch(Status.PENDING, match);
    }
}
