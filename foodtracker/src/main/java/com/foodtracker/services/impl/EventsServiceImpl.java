package com.foodtracker.services.impl;

import com.foodtracker.models.FoodTruck;
import com.foodtracker.models.FoodtrackerEvent;
import com.foodtracker.repositories.jpa.EventsRepository;
import com.foodtracker.repositories.jpa.TrucksRepository;
import com.foodtracker.services.EventsService;
import com.foodtracker.services.TruckService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventsServiceImpl implements EventsService {
  @Autowired
  EventsRepository eventRepository;

  @Override
  public List<FoodtrackerEvent> getAll() {
    return Lists.newArrayList(eventRepository.findAll());
  }

  @Override
  public List<FoodtrackerEvent> updateAll(List<FoodtrackerEvent> foodtrucks) {
    return Lists.newArrayList(eventRepository.saveAll(foodtrucks));
  }

  @Override
  public FoodtrackerEvent add(FoodtrackerEvent foodtruck) {
    return eventRepository.save(foodtruck);
  }
}
