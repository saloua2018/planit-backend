package com.descodeuses.planit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.descodeuses.planit.model.Contact;

@Repository
public interface ContactRepository extends  JpaRepository<Contact, Long>{

}
