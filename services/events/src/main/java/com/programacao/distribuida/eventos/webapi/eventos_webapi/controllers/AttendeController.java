package com.programacao.distribuida.eventos.webapi.eventos_webapi.controllers;

import java.util.List;
import java.util.Optional;

import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.AttendeDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.AttendeInsertDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.AttendeUpdateDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.entities.Attende;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.services.AttendeService;

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
@RequestMapping("/attendees")
@Tag(name = "Attende Controller")
public class AttendeController {

  @Autowired
  private AttendeService service;

  @GetMapping
  public ResponseEntity<List<AttendeDTO>> getAttendees(
      @RequestParam(name = "page", required = false, defaultValue = "0") Optional<String> page,
      @RequestParam(name = "limit", required = false, defaultValue = "5") Optional<String> limit) {
    Page<Attende> pageable = service.getAttendees(page, limit);

    HttpHeaders headers = new HttpHeaders();

    headers.set("X-Pagination-PageCount", String.valueOf(pageable.getNumberOfElements()));
    headers.set("X-Pagination-PageLimit", String.valueOf(pageable.getSize()));
    headers.set("X-Pagination-Page", String.valueOf(pageable.getPageable().getPageNumber()));
    headers.set("X-Pagination-TotalPage", String.valueOf(pageable.getTotalPages()));
    headers.set("X-Pagination-TotalElements", String.valueOf(pageable.getTotalElements()));

    return ResponseEntity.ok().headers(headers).body(service.toDTOList(pageable.getContent()));
  }

  @PostMapping
  public ResponseEntity<AttendeDTO> createAttendees(@RequestBody AttendeInsertDTO dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.createAttendees(dto));
  }

  @GetMapping("{attendeId}")
  public ResponseEntity<AttendeDTO> getAttendees(@PathVariable Long attendeId) {
    return ResponseEntity.ok(service.getAttendee(attendeId));
  }

  @PutMapping("{attendeId}")
  public ResponseEntity<AttendeDTO> updateAttendees(@PathVariable Long attendeId, @RequestBody AttendeUpdateDTO dto) {
    return ResponseEntity.ok(service.updateAttendees(attendeId, dto));
  }

  @DeleteMapping("{attendeId}")
  public ResponseEntity<Void> deleteAttendees(@PathVariable Long attendeId) {
    service.deleteAttendees(attendeId);

    return ResponseEntity.noContent().build();
  }
}
