package br.com.letscode.moviesbattle.controller;

import br.com.letscode.moviesbattle.entity.Game;
import br.com.letscode.moviesbattle.entity.Ranking;
import br.com.letscode.moviesbattle.entity.User;
import br.com.letscode.moviesbattle.service.MatchService;
import br.com.letscode.moviesbattle.service.RankingService;
import br.com.letscode.moviesbattle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ranking")
public class RankingController {

    private final RankingService rankingService;

    @Autowired
    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping
    public List<Ranking> getRanking(){
        return rankingService.createRanking();
    }

}
