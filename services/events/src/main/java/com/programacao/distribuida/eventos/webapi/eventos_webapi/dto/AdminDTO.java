package com.programacao.distribuida.eventos.webapi.eventos_webapi.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.programacao.distribuida.eventos.webapi.eventos_webapi.entities.Admin;

public class AdminDTO {

  private Long id;

  private String name;

  private String email;

  private String phoneNumber;

  private List<EventDTO> events;

  public AdminDTO(Admin entity) {
    setId(entity.getId());
    setName(entity.getName());
    setEmail(entity.getEmail());
    setPhoneNumber(entity.getPhoneNumber());
  }

  public AdminDTO(Admin entity, boolean shouldIncludeJoins) {
    setId(entity.getId());
    setName(entity.getName());
    setEmail(entity.getEmail());
    setPhoneNumber(entity.getPhoneNumber());

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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public List<EventDTO> getEvents() {
    return events;
  }

  public void setEvents(List<EventDTO> events) {
    this.events = events;
  }
}