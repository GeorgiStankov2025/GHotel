package org.ghotel.ghotel.common.security.service;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final String prefix = "Bearer ";

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @Override
    public void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            try {
                String token = header.substring(prefix.length());
                String username = jwtService.extractUsername(token);
                String role = jwtService.extractRole(token);

                if (username != null && jwtService.extractType(token).equals("ACCESS") &&
                        SecurityContextHolder.getContext().getAuthentication() == null) {
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);

                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(username, null, List.of(authority))
                    );
                }
            } catch (ExpiredJwtException ex) {
                log.warn("JWT token expired: {}", ex.getMessage());
            } catch (Exception ex) {
                log.warn("Invalid JWT token: {}", ex.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

}
