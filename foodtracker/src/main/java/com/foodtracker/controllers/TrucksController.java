package com.foodtracker.controllers;

import com.foodtracker.models.FoodTruck;
import com.foodtracker.services.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api/foodtracker/")
public class TrucksController {
  private TruckService truckService;

  @Autowired
  public void setTruckService(TruckService truckService) {
    this.truckService = truckService;
  }

  @GetMapping({"/trucks"})
  public ResponseEntity<?> getAll() {
    List<FoodTruck> response = truckService.getAll();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PutMapping({"/trucks"})
  public ResponseEntity<?> add( @RequestBody FoodTruck foodtruck) {
    System.out.println(foodtruck);
    FoodTruck response = truckService.add(foodtruck);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping({"/trucks"})
  public ResponseEntity<?> updateAll( @RequestBody List<FoodTruck> foodtrucks) {
    List<FoodTruck> response = truckService.updateAll(foodtrucks);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
