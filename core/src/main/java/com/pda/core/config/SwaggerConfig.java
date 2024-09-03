package com.pda.core.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class SwaggerConfig {

    public static final String JWT = "JWT";
    public static final String TRAVELER_ID = "travelerId";

    @Bean
    public OpenAPI openAPI() {
        String jwt = "JWT";

        return new OpenAPI()
                .components(new Components())
                .addServersItem(new Server().url("http://localhost"))
                .info(apiInfo())
                .components(new Components().addSecuritySchemes(JWT, new SecurityScheme()
                        .name(jwt)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat(JWT)
                        .in(SecurityScheme.In.HEADER)
                        .name(HttpHeaders.AUTHORIZATION)));
    }
    private Info apiInfo() {
        return new Info()
                .title("Stockmon Swagger UI")
                .description("Stockmon의 API 명세서 입니다.")
                .version("1.0.0");
    }
}