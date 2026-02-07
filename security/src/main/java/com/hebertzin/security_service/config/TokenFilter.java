package com.hebertzin.security_service.config;

import com.hebertzin.security_service.domain.TokenProvider;
import com.hebertzin.security_service.repository.UserRepository;
import com.hebertzin.security_service.repository.entities.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class TokenFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private  final UserRepository userRepository;

    public TokenFilter (TokenProvider tokenProvider, UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = this.extractTokenFromHeader(request);

        if (token != null) {
           String subject = this.tokenProvider.extracSubject(token);

           Optional<User> user = this.userRepository.findByEmail(subject);

           var authentication = new UsernamePasswordAuthenticationToken(user, subject);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null) {
            return null;
        }

        return authorizationHeader.replace("Bearer", "");
    }
}
