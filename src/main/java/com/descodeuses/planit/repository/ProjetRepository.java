package com.descodeuses.planit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.descodeuses.planit.model.Projet;

@Repository
public interface ProjetRepository extends  JpaRepository<Projet, Long>{

}
