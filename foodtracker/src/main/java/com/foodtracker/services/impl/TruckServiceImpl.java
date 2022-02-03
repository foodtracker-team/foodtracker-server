package com.foodtracker.services.impl;

import com.foodtracker.models.FoodTruck;
import com.foodtracker.repositories.jpa.TrucksRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.foodtracker.services.TruckService;
import java.util.*;

@Service
public class TruckServiceImpl implements TruckService {
  @Autowired
  TrucksRepository trucksRepository;

  @Override
  public List<FoodTruck> getAll() {
    return Lists.newArrayList(trucksRepository.findAll());
  }

  @Override
  public List<FoodTruck> updateAll(List<FoodTruck> foodtrucks) {
    return Lists.newArrayList(trucksRepository.saveAll(foodtrucks));
  }

  @Override
  public FoodTruck add(FoodTruck foodtruck) {
    return trucksRepository.save(foodtruck);
  }
}
