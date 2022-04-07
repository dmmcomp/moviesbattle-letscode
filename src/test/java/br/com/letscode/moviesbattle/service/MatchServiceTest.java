package br.com.letscode.moviesbattle.service;

import br.com.letscode.moviesbattle.ApplicationConfigTest;
import br.com.letscode.moviesbattle.utils.constant.Status;
import br.com.letscode.moviesbattle.entity.Game;
import br.com.letscode.moviesbattle.entity.Match;
import br.com.letscode.moviesbattle.entity.User;
import br.com.letscode.moviesbattle.repository.MatchRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayName("MatchServiceTest")
public class MatchServiceTest extends ApplicationConfigTest {

    @MockBean
    private MatchRepository matchRepository;

    @MockBean
    private GameService gameService;

    @Autowired
    private MatchService matchService;

    @Test
    @DisplayName("Inicialização de uma partida")
    void initMatch() {
        User user = new User();
        Match match = createMatch();
        Game game = createGame();
        Mockito.when(gameService.createOrGetActiveGame(match)).thenReturn(game);
        matchService.initMatch(user);
        Mockito.verify(matchRepository, Mockito.times(1)).save(ArgumentMatchers.any(Match.class));
    }

    @Test
    @DisplayName("Inclusão de pontuação na partida em caso de acerto da rodada")
    void updateMatchStatusWithWin() {
        Game game = createGame();
        Match match = createMatch();
        Mockito.when(game.getResult()).thenReturn(Status.WIN);
        Mockito.when(game.getMatch()).thenReturn(match);
        matchService.updateMatchStatus(game);
        Mockito.verify(match, Mockito.times(1)).addScore();
        Mockito.verify(matchRepository, Mockito.times(1)).save(ArgumentMatchers.any(Match.class));
    }

    @Test
    @DisplayName("Inclusão de erro na partida em caso de perda da rodada")
    void updateMatchStatusWithLose() {
        Game game = createGame();
        Match match = createMatch();
        Mockito.when(game.getResult()).thenReturn(Status.LOSE);
        Mockito.when(game.getMatch()).thenReturn(match);
        matchService.updateMatchStatus(game);
        Mockito.verify(match, Mockito.times(1)).addError();
        Mockito.verify(matchRepository, Mockito.times(1)).save(ArgumentMatchers.any(Match.class));
    }

    private Game createGame() {
        Game game = Mockito.mock(Game.class);
        return game;
    }

    private Match createMatch() {
        Match match = Mockito.mock(Match.class);
        return match;
    }
}
