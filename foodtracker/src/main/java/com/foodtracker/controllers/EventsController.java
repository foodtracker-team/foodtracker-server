package com.foodtracker.controllers;

import com.foodtracker.models.FoodTruck;
import com.foodtracker.models.FoodtrackerEvent;
import com.foodtracker.services.EventsService;
import com.foodtracker.services.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api/foodtracker/")
public class EventsController {
  private EventsService eventsService;

  @Autowired
  public void setEventsService(EventsService eventsService) {
    this.eventsService = eventsService;
  }

  @GetMapping({"/events"})
  public ResponseEntity<?> getAll() {
    List<FoodtrackerEvent> response = eventsService.getAll();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PutMapping({"/events"})
  public ResponseEntity<?> add( @RequestBody FoodtrackerEvent event) {
    System.out.println(event);
    FoodtrackerEvent response = eventsService.add(event);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping({"/events"})
  public ResponseEntity<?> updateAll( @RequestBody List<FoodtrackerEvent> events) {
    List<FoodtrackerEvent> response = eventsService.updateAll(events);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
