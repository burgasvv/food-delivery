package org.burgas.gatewayserver.config;

import org.burgas.gatewayserver.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationManager(authenticationManager())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(
                        authorizationManager -> authorizationManager

                                .requestMatchers(
                                        "/v3/api-docs","/v3/api-docs/**",
                                        "/identity-service/v3/api-docs","/identity-service/v3/api-docs/**",
                                        "/department-service/v3/api-docs", "/department-service/v3/api-docs/**",
                                        "/employee-service/v3/api-docs", "/employee-service/v3/api-docs/**",
                                        "/food-service/v3/api-docs", "/food-service/v3/api-docs/**",
                                        "/commit-service/v3/api-docs", "/commit-service/v3/api-docs/**",
                                        "/media-service/v3/api-docs", "/media-service/v3/api-docs/**",

                                        "/swagger-ui.html","/swagger-ui/**","/swagger-resources","/swagger-resources/**",
                                        "/configuration/ui","/configuration/security","/webjars/**",

                                        "/identity-service/swagger-ui.html","/identity-service/swagger-ui/**",
                                        "/identity-service/swagger-resources", "/identity-service/swagger-resources/**",
                                        "/identity-service/configuration", "/identity-service/configuration/**",
                                        "/identity-service/webjars/**",

                                        "/department-service/swagger-ui.html", "/department-service/swagger-ui/**",
                                        "/department-service/swagger-resources", "/department-service/swagger-resources/**",
                                        "/department-service/configuration", "/department-service/configuration/**",
                                        "/department-service/webjars/**",

                                        "/employee-service/swagger-ui.html", "/employee-service/swagger-ui/**",
                                        "/employee-service/swagger-resources", "/employee-service/swagger-resources/**",
                                        "/employee-service/configuration", "/employee-service/configuration/**",
                                        "/employee-service/webjars/**",

                                        "/food-service/swagger-ui.html", "/food-service/swagger-ui/**",
                                        "/food-service/swagger-resources", "/food-service/swagger-resources/**",
                                        "/food-service/configuration", "/food-service/configuration/**",
                                        "/food-service/webjars/**",

                                        "/commit-service/swagger-ui.html", "/commit-service/swagger-ui/**",
                                        "/commit-service/swagger-resources", "/commit-service/swagger-resources/**",
                                        "/commit-service/configuration", "/commit-service/configuration/**",
                                        "/commit-service/webjars/**",

                                        "/media-service/swagger-ui.html", "/media-service/swagger-ui/**",
                                        "/media-service/swagger-resources", "/media-service/swagger-resources/**",
                                        "/media-service/configuration", "/media-service/configuration/**",
                                        "/media-service/webjars/**"
                                )
                                .permitAll()

                                .requestMatchers(
                                        "/identities/create", "/authentication/principal",
                                        "/addresses","/addresses/by-id",
                                        "/departments","/departments/by-id",
                                        "/categories","/categories/by-id","/categories/by-name",
                                        "/capacities","/capacities/by-id","/capacities/by-liters",
                                        "/sizes", "/sizes/by-id", "/sizes/by-foodId",
                                        "/ingredients","/ingredients/by-id","/ingredients/by-name","/ingredients/by-foodId",
                                        "/food","/food/by-id","/food/by-name","/food/server-events",
                                        "/combos","/combos/by-id","/combos/by-name",
                                        "/choose/by-id","/choose/create","/choose/update","/choose/change-choose-amount",
                                        "/commits/get-commit","/commits/close-commit","/commits/delete-commit",
                                        "/media/by-id"
                                )
                                .permitAll()

                                .requestMatchers("/identities/**", "/authorities/**")
                                .hasAnyAuthority("ROLE_ADMIN","ROLE_EMPLOYEE","ROLE_USER")

                                .requestMatchers("/employees/**")
                                .hasAnyAuthority("ROLE_ADMIN","ROLE_EMPLOYEE")

                                .requestMatchers(
                                        "/addresses/create","/addresses/update","/addresses/delete",
                                        "/departments/create","/departments/update","/departments/delete",
                                        "/categories/create","/categories/update","/categories/delete",
                                        "/capacities/create","/capacities/update","/capacities/delete",
                                        "/sizes/create","/sizes/update","/sizes/delete",
                                        "/ingredients/create","/ingredients/update","/ingredients/delete",
                                        "/food/create","/food/update","/food/delete",
                                        "/food/upload-food-image","/food/delete-food-image",
                                        "/combos/create","/combos/update","/combos/delete",
                                        "/combos/upload-combo-image","/combos/delete-combo-image",
                                        "/media/upload","/media/delete"
                                )
                                .hasAnyAuthority("ROLE_ADMIN")
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
