package com.example.vatandas_sistemi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Mamak Belediyesi Vatandaş Şikayet-Öneri Sistemi API")
                        .description("Vatandaşların şikayet ve önerilerini yönetmek için geliştirilmiş RESTful API")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Mamak Belediyesi IT Ekibi")
                                .email("it@mamak.bel.tr")
                                .url("https://mamak.bel.tr"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Development Server"),
                        new Server()
                                .url("https://api.mamak.bel.tr")
                                .description("Production Server")
                ));
    }
} 