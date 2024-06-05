package ait.project.deutscheecke.security.sec_config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173") // Разрешенный источник (ваш фронтенд)
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Разрешенные HTTP методы
                .allowCredentials(true) // Разрешение передачи куки и заголовков авторизации
                .maxAge(3600); // Время жизни предварительных запросов (preflight requests)
    }
}
