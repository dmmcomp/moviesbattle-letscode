package br.com.letscode.moviesbattle.entity;

import br.com.letscode.moviesbattle.utils.constant.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Match {

    public Match(Status status, Integer errors, Integer score, User user){
        this.status = status;
        this.errors = errors;
        this.score = score;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer errors;

    private Integer score;

    private Status status;

    @JsonIgnore
    @ManyToOne
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "match")
    private List<Game> games;

    public void addError(){
        this.errors++;
        if(this.errors >= 3) this.status = Status.FINISHED;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void addScore(){
        this.score++;
    }

}
