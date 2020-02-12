package com.apiit.bangerandco.repositories;

import com.apiit.bangerandco.models.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle,Integer> {
}
