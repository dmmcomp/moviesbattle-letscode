package br.com.letscode.moviesbattle.entity;

import br.com.letscode.moviesbattle.utils.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Movie movie1;

    @ManyToOne
    private Movie movie2;

    private String key;

    private Status result;

    @ManyToOne
    private Match match;
}
