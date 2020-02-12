package com.apiit.bangerandco.repositories;

import com.apiit.bangerandco.models.Utility;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilityRepository extends CrudRepository<Utility,Integer> {
}
