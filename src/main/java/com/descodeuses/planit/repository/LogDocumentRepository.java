package com.descodeuses.planit.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.descodeuses.planit.model.LogDocument;

public interface LogDocumentRepository extends MongoRepository<LogDocument, String> {

}
