import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedOrigins(
                        "https://supreme-memory-6jpwrj594qq35jq5-5173.app.github.dev",
                        "https://supreme-memory-6jpwrj594qq35jq5-5174.app.github.dev",
                        "https://supreme-memory-6jpwrj594qq35jq5-5175.app.github.dev"
                    )
                    .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }
}