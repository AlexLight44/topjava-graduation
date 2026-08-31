package ru.javaops.topjava.graduation.app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
@OpenAPIDefinition(
        info = @Info(
                title = "Restaurant Voting REST API",
                version = "1.0",
                description = """
                        Lunch voting system.
                        <a href='https://github.com/JavaWebinar/topjava/blob/doc/doc/graduation.md'>Requirements</a>
                        <p>Users vote for a restaurant for today. A vote can be changed until 11:00.</p>
                        <p><b>Credentials:</b><br>
                        - user@yandex.ru / password<br>
                        - admin@gmail.com / admin</p>
                        """
        ),
        security = @SecurityRequirement(name = "basicAuth")
)
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("REST API")
                .pathsToMatch("/api/**")
                .build();
    }
}
