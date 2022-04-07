package br.com.letscode.moviesbattle.dto;

import br.com.letscode.moviesbattle.utils.constant.Status;
import br.com.letscode.moviesbattle.entity.Game;
import lombok.Data;

@Data
public class GameStatusResponse {
    private String movieAId;
    private String movieBId;
    private Double movieAScore;
    private Double movieBScore;
    private Status result;
    private Status matchStatus;
    public GameStatusResponse(Game game){
        this.movieAId = game.getMovie1().getId();
        this.movieBId = game.getMovie2().getId();
        this.movieAScore = game.getMovie1().getScore();
        this.movieBScore = game.getMovie2().getScore();
        this.result = game.getResult();
        this.matchStatus = game.getMatch().getStatus();

    }
}
