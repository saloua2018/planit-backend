package com.descodeuses.planit.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.descodeuses.planit.model.Utilisateur;

@Repository
public interface UserRepository extends JpaRepository<Utilisateur, Long>{
    Optional<Utilisateur> findByUsername(String username);
}
