package br.com.letscode.moviesbattle.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Ranking {
    private Integer matchCount;
    private Integer numPoints;
    private Integer numErrors;
    private Double finalScore;
    private User user;

    public Ranking() {
        this.matchCount = 0;
        this.numPoints = 0;
        this.numErrors = 0;
        this.finalScore = 0.0;
    }

    public void addMatchCount(){
        matchCount++;
    }

    public void addPoint(Integer score){
        numPoints += score;
    }

    public void addError(Integer errors){
        numErrors += errors;
    }
}
