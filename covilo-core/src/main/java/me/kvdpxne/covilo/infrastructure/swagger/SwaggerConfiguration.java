package me.kvdpxne.covilo.infrastructure.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

  @Bean
  public OpenAPI openApi() {
    return new OpenAPI()
      .info(
        new Info()
          .license(
            new License()
              .identifier("MIT")
              .name("MIT License")
              .url("https://raw.githubusercontent.com/kvdpxne/covilo/master/LICENSE")
          )
      )
      .addSecurityItem(
        new SecurityRequirement()
          .addList("bearerAuthorization")
      )
      .components(
        new Components()
          .addSecuritySchemes(
            "bearerAuthorization",
            new SecurityScheme()
              .name("Bearer Authorization")
              .description("Authorization using JWT.")
              .scheme("bearer")
              .bearerFormat("JWT")
              .type(Type.HTTP)
              .in(In.HEADER)
          )
      );
  }
}
