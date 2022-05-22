package com.natwest.currency.configuration;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("CurrencyDenomination API")
                        .description("Given a wallet of notes returns the minimum currency for an amount")
                        .version("v0.0.1"));
    }
}
