package com.programacao.distribuida.eventos.webapi.eventos_webapi.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.EventDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.entities.Event;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.entities.Place;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.repositories.EventRepository;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.repositories.PlaceRepository;

@Service
public class EventPlaceService {

  @Autowired
  private EventRepository eventRepository;

  @Autowired
  private PlaceRepository placeRepository;

  public List<EventDTO> getEventsByCity(String city) {
    List<Place> places = placeRepository.findByCity(city);
    if(CollectionUtils.isEmpty(places)){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foram encontrados eventos para essa cidade.");
    }
    List<Event> events = new ArrayList<>();
    for(Place place : places){
      List<Event> eventsAux = eventRepository.findEventIdsByPlace(place.getId());
      for(Event event : eventsAux){
        List<Place> placesAux = new ArrayList<>();
        placesAux.add(place);
        event.setPlaces(placesAux);
        events.add(event);
      }
    }
    if(CollectionUtils.isEmpty(events)){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foram encontrados eventos para essa cidade.");
    }
    return events.stream().map(e -> new EventDTO(e, true)).collect(Collectors.toList());
  }

  public void addPlaceToEvent(Long eventId, Long placeId) {
    Event event = eventRepository.findById(eventId).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "O Evento com essa identificação não foi encontrado."));

    List<Place> places = event.getPlaces();

    for (Place eventPlace : places) {
      if (eventPlace.getId() != placeId)
        continue;

      throw new ResponseStatusException(HttpStatus.CONFLICT, "Esse lugar já está associado a esse evento.");
    }

    Place place = placeRepository.findById(placeId).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "O Lugar com essa identificação não foi encontrado."));

    List<Event> placeEvents = place.getEvents();

    for(Event placeEvent : placeEvents) {
      if (!placeEvent.intersectsDateWith(event))
        continue;

      throw new ResponseStatusException(HttpStatus.CONFLICT, "Você não pode associar esse lugar com o evento porque já existe um evento no mesmo horário.");
    }

    places.add(place);

    eventRepository.save(event);
  }

  public void removePlaceFromEvent(Long eventId, Long placeId) {
    Event event = eventRepository.findById(eventId).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "O Evento com essa identificação não foi encontrado."));

    if (LocalDate.now().isAfter(event.getStartDate()) || (LocalDate.now().equals(event.getStartDate()) && LocalTime.now().isAfter(event.getStartTime())))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Você não pode remover esse lugar desse evento porque ele já ocorreu.");
  
    List<Place> places = event.getPlaces();

    boolean placeWasRemoved = places.removeIf(place -> place.getId() == placeId);

    eventRepository.save(event);

    if (placeWasRemoved)
      return;

    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Esse lugar não está associado a esse evento.");
  }
}
