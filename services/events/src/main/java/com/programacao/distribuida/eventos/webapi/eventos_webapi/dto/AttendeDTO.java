package com.programacao.distribuida.eventos.webapi.eventos_webapi.dto;

import com.programacao.distribuida.eventos.webapi.eventos_webapi.entities.Attende;

public class AttendeDTO {

  private Long id;

  private String name;

  private String email;

  private double balance;

  public AttendeDTO(Attende entity) {
    setId(entity.getId());
    setName(entity.getName());
    setEmail(entity.getEmail());
    setBalance(entity.getBalance());
  }

  public AttendeDTO(Attende entity, boolean shouldIncludeJoins) {
    setId(entity.getId());
    setName(entity.getName());
    setEmail(entity.getEmail());
    setBalance(entity.getBalance());
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

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }
}