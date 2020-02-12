package com.apiit.bangerandco.repositories;

import com.apiit.bangerandco.models.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends CrudRepository<Payment,Integer> {
}
