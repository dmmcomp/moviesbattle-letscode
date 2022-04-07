package br.com.letscode.moviesbattle.utils.doc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringDocConfig {


    @Bean
    public OpenAPI myOpenAPI() {
        return new OpenAPI().info(new Info().title("Movies Battle API")
                .description("Documentaçao da api proposta como desafio para avaliação no processo seletivo.")
                .version("v1"));
    }

}
