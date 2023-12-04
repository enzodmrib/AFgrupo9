package com.programacao.distribuida.eventos.webapi.eventos_webapi.controllers;

import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.EventDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.TicketInsertDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.services.TicketService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events/{eventId}/tickets")
@Tag(name = "Ticket Controller")
public class TicketController {

  @Autowired
  private TicketService service;

  @PostMapping
  public ResponseEntity<Void> sellTicket(@PathVariable Long eventId, @RequestBody TicketInsertDTO dto) {
    service.sellTicket(eventId, dto);

    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<EventDTO> getPlace(@PathVariable Long eventId) {
    return ResponseEntity.ok(service.getEventAndTickets(eventId));
  }

  @DeleteMapping
  public ResponseEntity<Void> deletePlace(@PathVariable Long eventId, @RequestBody TicketInsertDTO dto) {
    service.removeTicket(eventId, dto);

    return ResponseEntity.noContent().build();
  }
}
