package com.inatel.stockquotemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication(scanBasePackages = "com.inatel.stockquotemanager")
@EntityScan(basePackages = "com.inatel.stockquotemanager.model")
@EnableSwagger2
@EnableCaching
public class StockQuoteManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockQuoteManagerApplication.class, args);
    }

    @Bean
    public Docket swaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/**"))
                .apis(RequestHandlerSelectors.basePackage("com.inatel.stockquotemanager"))
                .build().apiInfo(apiDetails());
    }

    private ApiInfo apiDetails() {
        return new ApiInfo(
                "Stock Quote Management API",
                "API for recording and reading stock quotes",
                "1.0",
                "Free to use.",
                new springfox.documentation.service.Contact("Inatel", "http://inatel.com.br/", "contato@inatel.com.br"),
                "API License",
                "http://inatel.com.br/",
                Collections.emptyList()
        );

    }

}
