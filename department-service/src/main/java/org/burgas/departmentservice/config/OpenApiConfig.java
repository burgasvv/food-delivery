package org.burgas.departmentservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        servers = {
                @Server(url = "http://localhost:8765", description = "Gateway Server")
        },
        info = @Info(
                title = "API Department Service", version = "1.0",
                description = "Department Service - сервис управления отделами и точками для продажи продуктов и еды"
        )
)
public class OpenApiConfig {
}
