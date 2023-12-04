package com.programacao.distribuida.eventos.webapi.eventos_webapi.controllers;

import java.util.List;
import java.util.Optional;

import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.PlaceDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.PlaceInsertDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.PlaceUpdateDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.entities.Place;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.services.PlaceService;

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
@RequestMapping("/places")
@Tag(name = "Place Controller")
public class PlaceController {

  @Autowired
  private PlaceService service;

  @GetMapping
  public ResponseEntity<List<PlaceDTO>> getPlaces(
      @RequestParam(name = "page", required = false, defaultValue = "0") Optional<String> page,
      @RequestParam(name = "limit", required = false, defaultValue = "5") Optional<String> limit) {
    Page<Place> pageable = service.getPlaces(page, limit);

    HttpHeaders headers = new HttpHeaders();

    headers.set("X-Pagination-PageCount", String.valueOf(pageable.getNumberOfElements()));
    headers.set("X-Pagination-PageLimit", String.valueOf(pageable.getSize()));
    headers.set("X-Pagination-Page", String.valueOf(pageable.getPageable().getPageNumber()));
    headers.set("X-Pagination-TotalPage", String.valueOf(pageable.getTotalPages()));
    headers.set("X-Pagination-TotalElements", String.valueOf(pageable.getTotalElements()));

    return ResponseEntity.ok().headers(headers).body(service.toDTOList(pageable.getContent()));
  }

  @PostMapping
  public ResponseEntity<PlaceDTO> createPlace(@RequestBody PlaceInsertDTO dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.createPlace(dto));
  }

  @GetMapping("{placeId}")
  public ResponseEntity<PlaceDTO> getPlace(@PathVariable Long placeId) {
    return ResponseEntity.ok(service.getPlace(placeId));
  }

  @PutMapping("{placeId}")
  public ResponseEntity<PlaceDTO> updatePlace(@PathVariable Long placeId, @RequestBody PlaceUpdateDTO dto) {
    return ResponseEntity.ok(service.updatePlace(placeId, dto));
  }

  @DeleteMapping("{placeId}")
  public ResponseEntity<Void> deletePlace(@PathVariable Long placeId) {
    service.deletePlace(placeId);

    return ResponseEntity.noContent().build();
  }
}
