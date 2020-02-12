package com.apiit.bangerandco.repositories;

import com.apiit.bangerandco.models.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends CrudRepository<Booking,Integer> {
}
