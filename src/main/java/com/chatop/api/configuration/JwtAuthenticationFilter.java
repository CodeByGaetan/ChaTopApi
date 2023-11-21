package com.chatop.api.configuration;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.chatop.api.service.JwtService;
import com.chatop.api.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        String userEmail = "";

        // Si la route est login ou register, poursuivre directement les filtres de sécurité
        String url = request.getRequestURL().toString();
        if (url.endsWith("/auth/register") || url.endsWith("/auth/login") || url.contains("/images/")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Si un token est présent et valide, alors connexion à l'utilisateur et poursuite des filtres de sécurité
        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {

            jwt = authHeader.substring(7);

            try {
                userEmail = jwtService.extractUserName(jwt);
            } catch (Exception e) {
                // System.out.println("Error during username extraction");
            }
            
            if (!userEmail.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
                if (jwtService.isTokenValid(jwt, userDetails)) {

                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    context.setAuthentication(authToken);
                    SecurityContextHolder.setContext(context);

                    filterChain.doFilter(request, response);
                    return;
                }

            }
            
        }

        // Sinon le statut 401 est renvoyé
        response.setStatus(401);

    }

}