package br.com.letscode.moviesbattle.service;

import br.com.letscode.moviesbattle.constant.Status;
import br.com.letscode.moviesbattle.entity.Game;
import br.com.letscode.moviesbattle.entity.Match;
import br.com.letscode.moviesbattle.entity.Ranking;
import br.com.letscode.moviesbattle.entity.User;
import br.com.letscode.moviesbattle.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    private final GameService gameService;

    @Autowired
    public MatchService(MatchRepository matchRepository, GameService gameService) {
        this.matchRepository = matchRepository;
        this.gameService = gameService;
    }

    public Game initMatch(User user){
        Match match = save(new Match(Status.OPEN, 0, 0, user));
        return gameService.createOrGetActiveGame(match);
    }

    public Optional<Match> getActiveMatchByUser(User user) {
        return matchRepository.findFirstByStatusAndUser(Status.OPEN, user);

    }

    List<Match> getAll(){
        return matchRepository.findAll();
    }


    public void updateMatchStatus(Game game){
        Match match = game.getMatch();
        if(game.getResult().equals(Status.WIN)){
            match.addScore();
        }else if (game.getResult().equals(Status.LOSE)){
            match.addError();
        }
        save(match);
    }

    private Match save(Match match){
        return matchRepository.save(match);
    }

    public Optional<Match> getById(Long id){
        return matchRepository.findById(id);
    }
}
