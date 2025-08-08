package com.descodeuses.planit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.descodeuses.planit.model.Action;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long>{
    List<Action> findByUserId(Long userId);
}
