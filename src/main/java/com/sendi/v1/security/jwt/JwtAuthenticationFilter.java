package com.sendi.v1.security.jwt;

import com.sendi.v1.security.JpaUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final static int STARTING_POSITION_OF_TOKEN = 7;

    private final JwtService jwtService;
    private final JpaUserDetailsService jpaUserDetailsService;

    @Override
    protected void doFilterInternal (
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);

            log.info(authHeader);
            log.info("User might not have a bearer token");

            return;
        }

        jwtToken = authHeader.substring(STARTING_POSITION_OF_TOKEN);

        log.info(jwtToken);

        username = jwtService.extractUsername(jwtToken);

        log.info(username);

        if (username != null && userIsNotAuthenticated()) {
            UserDetails userDetails = this.jpaUserDetailsService.loadUserByUsername(username);

            log.info(String.valueOf(userDetails));

            if (jwtService.isTokenValid(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                log.info(String.valueOf(authenticationToken));

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                log.info(String.valueOf(new WebAuthenticationDetailsSource().buildDetails(request)));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean userIsNotAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() == null;
    }
}
