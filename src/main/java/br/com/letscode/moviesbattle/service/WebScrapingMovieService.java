package br.com.letscode.moviesbattle.service;

import br.com.letscode.moviesbattle.entity.Movie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WebScrapingMovieService {

    private final MovieService movieService;

    @Autowired
    public WebScrapingMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    public void execute() throws IOException {
        String url = "https://www.imdb.com/chart/moviemeter/?ref_=nv_mv_mpm";
        Document doc = Jsoup.connect(url).get();

        System.out.println("Inicializando coleta de filmes, aguarde a finalização");
        Elements movieList = doc.getElementsByClass("chart full-width").first()
                .getElementsByTag("tbody").first()
                .getElementsByTag("tr");
        for (Element movieElement: movieList) {
            Element titleElement = movieElement.getElementsByClass("titleColumn").first();
            Element titleTag = titleElement.getElementsByTag("a").first();
            String hrefValue = titleTag.attributes().get("href");

            String id = hrefValue.substring(hrefValue.indexOf("tt"),hrefValue.indexOf("/?"));
            String title = titleTag.text();

            Element ratingTag = movieElement.getElementsByClass("ratingColumn imdbRating").first();
            Element scoreTag = ratingTag.getElementsByTag("strong").first();
            if(scoreTag != null){
                String score = scoreTag.text();
                Movie movie = new Movie(id,title,Double.parseDouble(score));
                movieService.save(movie);
            }
        }
        System.out.println("Coleta de filmes finalizada com sucesso");
    }
}
