package team.cobblestone.gikipedia.server.v1.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .addSecurityItem(securityRequirement())
                .components(components());
    }

    private Info apiInfo() {
        return new Info()
                .title("Gikipedia API")
                .description("Gikipedia Wiki System REST API Documentation")
                .version("1.0.0");
    }

    private SecurityRequirement securityRequirement() {
        return new SecurityRequirement().addList("bearerAuth");
    }

    private Components components() {
        return new Components()
                .addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"));
    }

}