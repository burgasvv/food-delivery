package org.burgas.employeeservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        servers = {
                @Server(url = "http://localhost:8765", description = "Gateway Server")
        },
        info = @Info(
                title = "API Employee Service", version = "1.0",
                description = "Employee Service - сервис API для сотрудников"
        )
)
public class OpenApiConfig {
}
