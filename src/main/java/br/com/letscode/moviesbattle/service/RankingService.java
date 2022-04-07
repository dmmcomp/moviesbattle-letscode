package br.com.letscode.moviesbattle.service;

import br.com.letscode.moviesbattle.entity.Match;
import br.com.letscode.moviesbattle.entity.Ranking;
import br.com.letscode.moviesbattle.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;

@Service
public class RankingService {
    private final MatchService matchService;
    private final UserService userService;

    @Autowired
    public RankingService(MatchService matchService, UserService userService) {
        this.matchService = matchService;
        this.userService = userService;
    }

    public List<Ranking> createRanking(){
        List<Ranking> rankingTotal = new ArrayList<>();
        List<Match> allMatches = matchService.getAll();

        if(allMatches.size() == 0) return rankingTotal;

        Map<String, List<Match>> postsPerType = allMatches.stream()
                .collect(groupingBy(match -> match.getUser().getUsername()));



        postsPerType.forEach((username, matches) -> {
                Ranking ranking = new Ranking();
                Optional<User> user = userService.findByUsername(username);
                if(user.isPresent()) {
                    ranking.setUser(user.get());
                    matches.forEach(match -> {
                        ranking.addMatchCount();
                        ranking.addPoint(match.getScore());
                        ranking.addError(match.getErrors());
                    });
                    ranking.setFinalScore(calculateFinalScore(ranking));
                    rankingTotal.add(ranking);
                }
        }
        );

        Collections.sort(rankingTotal, Collections.reverseOrder(Comparator.comparingDouble(Ranking ::getFinalScore)));
        return rankingTotal;
    }

    public Double calculateFinalScore(Ranking ranking){
        Double finalScore = ((double) ranking.getNumPoints() / ((double) ranking.getNumPoints() + (double) ranking.getNumErrors())) * (double) ranking.getMatchCount();
        return finalScore;
    }
}
