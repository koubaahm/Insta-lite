package univ_rouen.fr.Insta_lite.configuration;


import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi apiInstaLite() {
        return GroupedOpenApi.builder()
                .group("InstaLite")
                .packagesToScan("univ_rouen.fr.Insta_lite.controllers")
                .pathsToMatch("/api/auth/**","/api/users/**","/api/videos/**","/api/images/**","/api/comments/**")
                .build();
    }
}

