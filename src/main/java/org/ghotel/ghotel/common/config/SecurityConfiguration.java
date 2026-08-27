package org.ghotel.ghotel.common.config;

import jakarta.servlet.http.HttpServletResponse;
import org.ghotel.ghotel.common.security.JwtFilter;
import org.ghotel.ghotel.entity.EmployeeRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtFilter jwtFilter;

    public SecurityConfiguration(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy
                        (SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(((
                                request,
                                response,
                                authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")))
                        .accessDeniedHandler((
                                request,
                                response,
                                accessDeniedException) ->
                                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden"))
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers(
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .requestMatchers("/api/v1/employee/**").hasAnyAuthority(EmployeeRole.ROLE_ADMIN.name())
                        .requestMatchers("/api/v1/reservation/**").hasAnyAuthority(
                                EmployeeRole.ROLE_RECEPTIONIST.name(), EmployeeRole.ROLE_ADMIN.name())
                        .requestMatchers("/api/v1/room/**").hasAnyAuthority(
                                EmployeeRole.ROLE_RECEPTIONIST.name(), EmployeeRole.ROLE_ADMIN.name())
                        .requestMatchers("/api/v1/customer/**").hasAnyAuthority(
                                EmployeeRole.ROLE_RECEPTIONIST.name(), EmployeeRole.ROLE_ADMIN.name())
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();


    }
}
