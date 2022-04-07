package br.com.letscode.moviesbattle;


import br.com.letscode.moviesbattle.entity.Game;
import br.com.letscode.moviesbattle.entity.Match;
import br.com.letscode.moviesbattle.entity.Movie;
import br.com.letscode.moviesbattle.entity.User;
import br.com.letscode.moviesbattle.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class InitializerRunner implements ApplicationRunner {

    private final MovieService movieService;
    private final UserService userService;
    private final WebScrapingMovieService webScrapingMovieService;

    @Autowired
    public InitializerRunner(MovieService movieService, UserService userService, WebScrapingMovieService webScrapingMovieService) {
        this.movieService = movieService;
        this.userService = userService;
        this.webScrapingMovieService = webScrapingMovieService;
    }

    @Override
    public void run(ApplicationArguments args){

        try{
            webScrapingMovieService.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
