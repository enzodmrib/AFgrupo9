package com.programacao.distribuida.eventos.webapi.eventos_webapi.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.EventDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.EventInsertDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.EventUpdateDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.entities.Event;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.services.EventService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
@Tag(name = "Event Controller")
public class EventController {

  @Autowired
  private EventService service;

  @GetMapping
  public ResponseEntity<List<EventDTO>> getEvents(
      @RequestParam(name = "page", required = false, defaultValue = "0") Optional<String> page,
      @RequestParam(name = "limit", required = false, defaultValue = "5") Optional<String> limit,
      @RequestParam(name = "name", required = false) Optional<String> name,
      @RequestParam(name = "place", required = false) Optional<String> place,
      @RequestParam(name = "description", required = false) Optional<String> description,
      @RequestParam(name = "emailContact", required = false) Optional<String> emailContact,
      @RequestParam(name = "startDate", required = false) Optional<String> startDate) {
    Page<Event> pageable = service.getEvents(page, limit, name, place, description, emailContact, Optional.ofNullable(startDate.isEmpty() ? null : LocalDate.parse(startDate.get())));

    HttpHeaders headers = new HttpHeaders();

    headers.set("X-Pagination-PageCount", String.valueOf(pageable.getNumberOfElements()));
    headers.set("X-Pagination-PageLimit", String.valueOf(pageable.getSize()));
    headers.set("X-Pagination-Page", String.valueOf(pageable.getPageable().getPageNumber()));
    headers.set("X-Pagination-TotalPage", String.valueOf(pageable.getTotalPages()));
    headers.set("X-Pagination-TotalElements", String.valueOf(pageable.getTotalElements()));

    return ResponseEntity.ok().headers(headers).body(service.toDTOList(pageable.getContent()));
  }

  @PostMapping
  public ResponseEntity<EventDTO> createEvent(@RequestBody EventInsertDTO dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.createEvent(dto));
  }

  @GetMapping("{eventId}")
  public ResponseEntity<EventDTO> getEvent(@PathVariable Long eventId) {
    return ResponseEntity.ok(service.getEvent(eventId));
  }

  @PutMapping("{eventId}")
  public ResponseEntity<EventDTO> updateEvent(@PathVariable Long eventId, @RequestBody EventUpdateDTO dto) {
    return ResponseEntity.ok(service.updateEvent(eventId, dto));
  }

  @DeleteMapping("{eventId}")
  public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) {
    service.deleteEvent(eventId);

    return ResponseEntity.noContent().build();
  }
}
