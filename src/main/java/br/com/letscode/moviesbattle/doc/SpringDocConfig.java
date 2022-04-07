package br.com.letscode.moviesbattle.doc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

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
