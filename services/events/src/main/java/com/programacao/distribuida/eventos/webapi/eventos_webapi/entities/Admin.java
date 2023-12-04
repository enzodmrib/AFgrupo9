package com.programacao.distribuida.eventos.webapi.eventos_webapi.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.AdminInsertDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.entities.common.BaseUser;

@Entity
@DiscriminatorValue("admin")
public class Admin extends BaseUser {

  private String phoneNumber;

  @OneToMany(
    mappedBy = "admin",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<Event> events;

  public Admin() {

  }

  public Admin(AdminInsertDTO admin) {
    setName(admin.getName());
    setEmail(admin.getEmail());
    setPhoneNumber(admin.getPhoneNumber());
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public List<Event> getEvents() {
    return events;
  }

  public void setEvents(List<Event> events) {
    this.events = events;
  }
}
