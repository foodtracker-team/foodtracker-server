package com.foodtracker.repositories.jpa;

import com.foodtracker.models.FoodTruck;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrucksRepository extends CrudRepository<FoodTruck, Long> {

}
