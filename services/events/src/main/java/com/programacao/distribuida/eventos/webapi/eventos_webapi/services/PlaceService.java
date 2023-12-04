package com.programacao.distribuida.eventos.webapi.eventos_webapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.PlaceDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.PlaceInsertDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.PlaceUpdateDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.entities.Event;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.entities.Place;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.repositories.PlaceRepository;

@Service
public class PlaceService {

  @Autowired
  private PlaceRepository repository;

  public Page<Place> getPlaces(Optional<String> pageString, Optional<String> limitString) {
    int page = Math.max(Integer.parseInt(pageString.orElse("0")), 0);
    int limit = Math.min(Math.max(Integer.parseInt(limitString.orElse("5")), 5), 2000);

    return repository.findAll(PageRequest.of(page, limit));
  }

  public PlaceDTO getPlace(Long placeId) {
    Place entity = repository.findById(placeId).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "O Lugar com essa identificação não foi encontrado."));

    return new PlaceDTO(entity);
  }

  public PlaceDTO createPlace(PlaceInsertDTO dto) {
    Place entity = new Place(dto);

    entity = repository.save(entity);

    return new PlaceDTO(entity);
  }

  public PlaceDTO updatePlace(Long placeId, PlaceUpdateDTO dto) {
    Place entity = repository.findById(placeId).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "O Lugar com essa identificação não foi encontrado."));

    entity.setName(dto.getName());
    entity.setAddress(dto.getAddress());

    entity = repository.save(entity);

    return new PlaceDTO(entity);
  }

  public void deletePlace(Long placeId) {
    Place entity = repository.findById(placeId).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "O Lugar com essa identificação não foi encontrado."));

    List<Event> events = entity.getEvents();

    if (events.size() > 0)
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Você não pode remover esse lugar porque ele já está associado com algum evento.");

    try {
      repository.deleteById(placeId);
    } catch (EmptyResultDataAccessException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O Lugar com essa identificação não foi encontrado.");
    }
  }

  public List<PlaceDTO> toDTOList(List<Place> list) {
    List<PlaceDTO> listDTO = new ArrayList<>();

    for (Place Place : list) {
      PlaceDTO dto = new PlaceDTO(Place, true);

      listDTO.add(dto);
    }

    return listDTO;
  }
}
