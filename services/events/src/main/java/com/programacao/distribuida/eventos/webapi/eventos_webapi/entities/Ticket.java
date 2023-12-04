package com.programacao.distribuida.eventos.webapi.eventos_webapi.entities;

import java.io.Serializable;
import java.time.Instant;

import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.TicketInsertDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.TicketType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_TICKET")
public class Ticket implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.ORDINAL)
  private TicketType type;

  private Instant date;

  private double price;

  @ManyToOne(cascade = CascadeType.PERSIST)
  private Attende attende;

  @ManyToOne(cascade = CascadeType.PERSIST)
  private Event event;

  public Ticket() {

  }

  public Ticket(TicketInsertDTO dto) {
    setType(dto.getType());
    setDate(Instant.now());
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

  public Attende getAttende() {
    return attende;
  }

  public void setAttende(Attende attende) {
    this.attende = attende;
  }

  public Event getEvent() {
    return event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }
}
