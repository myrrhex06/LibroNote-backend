package com.libronote.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(info())
                .components(new Components());
    }

    public Info info() {
        return new Info()
                .title("Libronote Backend API Docs")
                .version("1.0.0")
                .description("Libronote - 독서 관리 웹 백엔드");
    }
}
