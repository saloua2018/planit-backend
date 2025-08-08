package com.descodeuses.planit.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtFilter extends OncePerRequestFilter{// Injection de JwtUtil pour gérer les opérations liées au token JWT (extraction du nom d'utilisateur, validation, etc.)
    @Autowired
    private JwtUtil jwtUtil;

    // Injection de UserDetailsService pour récupérer les informations de l'utilisateur depuis la base de données
    @Autowired
    private UserDetailsService userDetailsService;

    // Méthode appelée automatiquement à chaque requête HTTP, une seule fois (car hérite de OncePerRequestFilter)
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Récupération de l'en-tête "Authorization" de la requête
        final String authHeader = request.getHeader("Authorization");

        String username = null; // Pour stocker le nom d'utilisateur extrait du token
        String jwt = null;      // Pour stocker le token JWT

        // Si l'en-tête commence par "Bearer ", on extrait le token et le nom d'utilisateur
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7); // Enlève le préfixe "Bearer "
            username = jwtUtil.extractUsername(jwt); // Extrait le nom d'utilisateur depuis le token
        }

        // Si un nom d'utilisateur a été extrait et qu'aucun utilisateur n'est encore authentifié dans le contexte de sécurité
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Charge les détails de l'utilisateur (rôles, mot de passe, etc.) depuis la base
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Vérifie si le token est valide pour cet utilisateur
            if (jwtUtil.validateToken(jwt, userDetails)) {
                // Crée un objet d'authentification avec les détails de l'utilisateur et ses rôles
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // Associe les détails de la requête (ex. adresse IP, session) à l'objet d'authentification
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Enregistre l'utilisateur comme authentifié dans le contexte de sécurité de Spring
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Passe la requête au filtre suivant dans la chaîne
        filterChain.doFilter(request, response);
    }
}
