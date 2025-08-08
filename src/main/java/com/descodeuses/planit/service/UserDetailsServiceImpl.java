package com.descodeuses.planit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.descodeuses.planit.model.Utilisateur;
import com.descodeuses.planit.repository.UserRepository;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService{
    // Injection du repository permettant d'accéder à la base de données des utilisateurs
    @Autowired
    private UserRepository utilisateurRepository;

    // Méthode obligatoire à implémenter : permet à Spring Security de charger un utilisateur via son username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // On cherche l'utilisateur dans la base par son username (login)
        // Si non trouvé, on lève une exception
        Utilisateur user = utilisateurRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // On retourne un objet Spring Security `User` (qui implémente UserDetails)
        // Il contient : le nom d’utilisateur, le mot de passe, et une liste de rôles (authorities)
        return new User(
            user.getUsername(), // identifiant
            user.getPassword(), // mot de passe (doit être déjà encodé)
            List.of(new SimpleGrantedAuthority(user.getRole())) // autorité/role ex: ROLE_ADMIN
        );
    }
}
