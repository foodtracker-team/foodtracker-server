package com.foodtracker.services;

import com.foodtracker.models.FoodTruck;
import com.foodtracker.models.FoodtrackerEvent;

import java.util.List;

public interface EventsService {
  List<FoodtrackerEvent> getAll();

  List<FoodtrackerEvent> updateAll(List<FoodtrackerEvent> events);

  FoodtrackerEvent add(FoodtrackerEvent event);
}
