package com.apiit.bangerandco.repositories;

import com.apiit.bangerandco.models.Document;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends CrudRepository<Document,Long> {
}
