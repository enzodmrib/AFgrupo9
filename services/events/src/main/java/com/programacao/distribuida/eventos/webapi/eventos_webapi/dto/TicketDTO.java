package com.programacao.distribuida.eventos.webapi.eventos_webapi.dto;

import java.time.Instant;

import com.programacao.distribuida.eventos.webapi.eventos_webapi.entities.Ticket;

public class TicketDTO {

  private Long id;

  private TicketType type;

  private Instant date;

  private double price;

  public TicketDTO(Ticket entity) {
    setId(entity.getId());
    setType(entity.getType());
    setDate(entity.getDate());
    setPrice(entity.getPrice());
  }

  public TicketDTO(Ticket entity, boolean shouldIncludeJoins) {
    this(entity);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public TicketType getType() {
    return type;
  }

  public void setType(TicketType type) {
    this.type = type;
  }

  public Instant getDate() {
    return date;
  }

  public void setDate(Instant date) {
    this.date = date;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }
}