package com.nds.ssagdaglo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

public class SwaggerConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("v1-definition")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public OpenAPI ssagDagloAPI() {
        return new OpenAPI()
                .info(new Info().title("SSAGDAGLO API")
                        .description("싹다글로 프로젝트 API 명세서")
                        .version("v0.0.1"));
    }

}
