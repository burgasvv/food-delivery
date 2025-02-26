package org.burgas.mediaservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        servers = @Server(
                url = "http://localhost:8765", description = "Gateway Server"
        ),
        info = @Info(
                title = "API Media Service", version = "1.0",
                description = "Media Service - сервис для хранения и обменом медиа данными"
        )
)
public class OpenApiConfig {
}
