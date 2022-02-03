package com.foodtracker.repositories.jpa;

import com.foodtracker.models.FoodTruck;
import com.foodtracker.models.FoodtrackerEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsRepository extends CrudRepository<FoodtrackerEvent, Long> {

}
