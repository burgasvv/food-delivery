package org.burgas.foodservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        servers = {
                @Server(url = "http://localhost:8765", description = "Gateway Server")
        },
        info = @Info(
                title = "API Food Service", version = "1.0",
                description = "Food Service - сервис предоставления меню и еды"
        )
)
public class OpenApiConfig {
}
