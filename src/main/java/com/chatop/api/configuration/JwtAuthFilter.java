package com.chatop.api.configuration;

import java.io.IOException;
import java.util.Arrays;

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
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        String userEmail = "";

        // If the route is login, register or images then continue directly the security
        // chain
        String url = request.getRequestURL().toString();
        boolean isAllowedRoute = Arrays.stream(SpringSecurityConfiguration.allowedRoutes).anyMatch(entry -> {
            if (entry.contains("**")) {
                return url.contains(entry.replace("/**", ""));
            } else {
                return url.endsWith(entry);
            }
        });
        if (isAllowedRoute) {
            filterChain.doFilter(request, response);
            return;
        }

        // If a valid token in present : connect to the corresponding user and continue
        // de security chain
        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {

            jwt = authHeader.substring(7);

            try {
                userEmail = jwtService.extractUserName(jwt);
            } catch (Exception e) {
                // The token is expired, invalid or the user can't be found
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

        // Else the 401 status is send back
        response.setStatus(401);

    }

}