package com.programacao.distribuida.eventos.webapi.eventos_webapi.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.PlaceInsertDTO;

@Entity
@Table(name = "TB_PLACE")
public class Place implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String address;

  private String city;

  private String state;

  private String country;

  @ManyToMany(mappedBy="places")
  private List<Event> events = new ArrayList<>();

  public Place() {

  }

  public Place(PlaceInsertDTO dto) {
    this.name = dto.getName();
    this.address = dto.getAddress();
    this.city = dto.getCity();
    this.state = dto.getState();
    this.country = dto.getCountry();
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

  public List<Event> getEvents() {
    return events;
  }

  public void setEvents(List<Event> events) {
    this.events = events;
  }
}
