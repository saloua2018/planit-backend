package com.descodeuses.planit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.descodeuses.planit.model.Utilisateur;
import com.descodeuses.planit.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Utilisateur createUser(Utilisateur utilisateur) throws Exception{
        if (emailExist(utilisateur.getUsername())) {
            throw new Exception("user already exist");
        }
        Utilisateur user = new Utilisateur();
        user.setUsername(utilisateur.getUsername());
        user.setPassword(passwordEncoder.encode(utilisateur.getPassword()) );
        
        // user.setRole("ROLE_"+utilisateur.getRole().toUpperCase());
        user.setRole("ROLE_USER");
        
        return utilisateurRepository.save(user);

    }

    private boolean emailExist(String email){
        System.out.println("email: "+email);
        return utilisateurRepository.findByUsername(email).isPresent();
    }

    // @Override



}
