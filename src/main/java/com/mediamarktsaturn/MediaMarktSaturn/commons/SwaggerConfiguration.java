package com.mediamarktsaturn.MediaMarktSaturn.commons;

import org.springframework.context.annotation.Bean;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket swaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mediamarktsaturn"))
                .build()
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails() {
        return new ApiInfo(
                "MediaMarkt & Saturn API",
                "Sample API for Interview",
                "1.0",
                "Showing purposes only",
                new springfox.documentation.service.Contact(
                        "Nemanja Milosavljevic",
                        "https://github.com/NemanyaM",
                        "nemanjovski@gmail.com"),
                "API Licence",
                "",
                Collections.emptyList()
        );
    }
}
