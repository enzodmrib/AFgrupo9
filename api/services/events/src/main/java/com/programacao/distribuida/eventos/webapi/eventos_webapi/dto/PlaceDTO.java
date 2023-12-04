package com.programacao.distribuida.eventos.webapi.eventos_webapi.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.programacao.distribuida.eventos.webapi.eventos_webapi.entities.Place;

public class PlaceDTO {

  private Long id;

  private String name;

  private String address;

  private String city;

  private String state;

  private String country;

  private List<EventDTO> events;

  public PlaceDTO(Place entity) {
    setId(entity.getId());
    setName(entity.getName());
    setAddress(entity.getAddress());
    setCity(entity.getCity());
    setState(entity.getState());
    setCountry(entity.getCountry());
  }

  public PlaceDTO(Place entity, boolean shouldIncludeJoins) {
    this(entity);

    if (shouldIncludeJoins)
      setEvents(entity.getEvents().stream().map(event -> new EventDTO(event, false)).collect(Collectors.toList()));
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public List<EventDTO> getEvents() {
    return events;
  }

  public void setEvents(List<EventDTO> events) {
    this.events = events;
  }
}