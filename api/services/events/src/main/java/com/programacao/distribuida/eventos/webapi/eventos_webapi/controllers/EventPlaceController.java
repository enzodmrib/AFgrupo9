package com.programacao.distribuida.eventos.webapi.eventos_webapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.EventDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.services.EventPlaceService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
// @RequestMapping("/events/{eventId}/places/{placeId}")
@RequestMapping("/events-places")
@Tag(name = "Event - Place Controller")
public class EventPlaceController {

  @Autowired
  private EventPlaceService service;

  @GetMapping("/city/{city}")
  public ResponseEntity<List<EventDTO>> getEventsByCity(@PathVariable String city) {
    return ResponseEntity.ok(service.getEventsByCity(city));
  }

  @PostMapping("/{eventId}/{placeId}")
  public ResponseEntity<Void> addPlaceToEvent(@PathVariable Long eventId, @PathVariable Long placeId) {
    service.addPlaceToEvent(eventId, placeId);
    
    return ResponseEntity.noContent().build();
  }
  
  @DeleteMapping
  @PostMapping("/{eventId}/{placeId}")
  public ResponseEntity<Void> removePlaceToEvent(@PathVariable Long eventId, @PathVariable Long placeId) {
    service.removePlaceFromEvent(eventId, placeId);

    return ResponseEntity.noContent().build();
  }
}
