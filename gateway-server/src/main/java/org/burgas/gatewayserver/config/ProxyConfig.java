package org.burgas.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
public class ProxyConfig {

    public static final String DATABASE_SERVICE_URL = "http://localhost:8080";
    public static final String IDENTITY_SERVICE_URL = "http://localhost:8888";
    public static final String DEPARTMENT_SERVICE_URL = "http://localhost:9000";
    public static final String EMPLOYEE_SERVICE_URL = "http://localhost:9010";
    public static final String FOOD_SERVICE_URL = "http://localhost:9020";
    public static final String COMMIT_SERVICE_URL = "http://localhost:9030";
    public static final String MEDIA_SERVICE_URL = "http://localhost:9040";

    @Bean
    public RouterFunction<ServerResponse> gatewayProxyRoutes() {
        return RouterFunctions.route()

                .GET("/tokens/**", http(DATABASE_SERVICE_URL))
                .POST("/tokens/**", http(DATABASE_SERVICE_URL))

                .GET("/authorities/**", http(IDENTITY_SERVICE_URL))
                .POST("/authorities/**", http(IDENTITY_SERVICE_URL))
                .PUT("/authorities/**", http(IDENTITY_SERVICE_URL))
                .DELETE("/authorities/**", http(IDENTITY_SERVICE_URL))
                .GET("/identities/**", http(IDENTITY_SERVICE_URL))
                .POST("/identities/**", http(IDENTITY_SERVICE_URL))
                .PUT("/identities/**", http(IDENTITY_SERVICE_URL))
                .DELETE("/identities/**", http(IDENTITY_SERVICE_URL))
                .GET("/identity-service/v3/api-docs", http(IDENTITY_SERVICE_URL))
                .GET("/identity-service/v3/api-docs/**", http(IDENTITY_SERVICE_URL))

                .GET("/addresses/**", http(DEPARTMENT_SERVICE_URL))
                .POST("/addresses/**", http(DEPARTMENT_SERVICE_URL))
                .DELETE("/addresses/**", http(DEPARTMENT_SERVICE_URL))
                .GET("/departments/**", http(DEPARTMENT_SERVICE_URL))
                .POST("/departments/**", http(DEPARTMENT_SERVICE_URL))
                .DELETE("/departments/**", http(DEPARTMENT_SERVICE_URL))
                .GET("/department-service/v3/api-docs", http(DEPARTMENT_SERVICE_URL))
                .GET("/department-service/v3/api-docs/**", http(DEPARTMENT_SERVICE_URL))

                .GET("/employees/**", http(EMPLOYEE_SERVICE_URL))
                .POST("/employees/**", http(EMPLOYEE_SERVICE_URL))
                .DELETE("/employees/**", http(EMPLOYEE_SERVICE_URL))
                .GET("/employee-service/v3/api-docs", http(EMPLOYEE_SERVICE_URL))
                .GET("/employee-service/v3/api-docs/**", http(EMPLOYEE_SERVICE_URL))

                .GET("/categories/**", http(FOOD_SERVICE_URL))
                .POST("/categories/**", http(FOOD_SERVICE_URL))
                .DELETE("/categories/**", http(FOOD_SERVICE_URL))
                .GET("/capacities/**", http(FOOD_SERVICE_URL))
                .POST("/capacities/**", http(FOOD_SERVICE_URL))
                .DELETE("/capacities/**", http(FOOD_SERVICE_URL))
                .GET("/sizes/**", http(FOOD_SERVICE_URL))
                .POST("/sizes/**", http(FOOD_SERVICE_URL))
                .DELETE("/sizes/**", http(FOOD_SERVICE_URL))
                .GET("/ingredients/**", http(FOOD_SERVICE_URL))
                .POST("/ingredients/**", http(FOOD_SERVICE_URL))
                .DELETE("/ingredients/**", http(FOOD_SERVICE_URL))
                .GET("/food/**", http(FOOD_SERVICE_URL))
                .POST("/food/**", http(FOOD_SERVICE_URL))
                .DELETE("/food/**", http(FOOD_SERVICE_URL))
                .GET("/combos/**", http(FOOD_SERVICE_URL))
                .POST("/combos/**", http(FOOD_SERVICE_URL))
                .DELETE("/combos/**", http(FOOD_SERVICE_URL))
                .GET("/food-service/v3/api-docs", http(FOOD_SERVICE_URL))
                .GET("/food-service/v3/api-docs/**", http(FOOD_SERVICE_URL))

                .GET("/choose/**", http(COMMIT_SERVICE_URL))
                .POST("/choose/**", http(COMMIT_SERVICE_URL))
                .DELETE("/choose/**", http(COMMIT_SERVICE_URL))
                .GET("/commits/**", http(COMMIT_SERVICE_URL))
                .POST("/commits/**", http(COMMIT_SERVICE_URL))
                .DELETE("/commits/**", http(COMMIT_SERVICE_URL))
                .GET("/commit-service/v3/api-docs", http(COMMIT_SERVICE_URL))
                .GET("/commit-service/v3/api-docs/**", http(COMMIT_SERVICE_URL))

                .GET("/media/**", http(MEDIA_SERVICE_URL))
                .POST("/media/**", http(MEDIA_SERVICE_URL))
                .DELETE("/media/**", http(MEDIA_SERVICE_URL))
                .GET("/media-service/v3/api-docs", http(MEDIA_SERVICE_URL))
                .GET("/media-service/v3/api-docs/**", http(MEDIA_SERVICE_URL))

                .build();
    }
}
