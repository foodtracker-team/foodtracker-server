package com.foodtracker.services;

import com.foodtracker.models.FoodTruck;

import java.util.List;

public interface TruckService {
  List<FoodTruck> getAll();

  List<FoodTruck> updateAll(List<FoodTruck> foodtrucks);

  FoodTruck add(FoodTruck foodtruck);
}
