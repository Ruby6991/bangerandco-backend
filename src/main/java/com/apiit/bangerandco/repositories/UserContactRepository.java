package com.apiit.bangerandco.repositories;

import com.apiit.bangerandco.models.UserContact;
import com.apiit.bangerandco.models.UserContactID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContactRepository extends CrudRepository<UserContact, UserContactID> {
}
