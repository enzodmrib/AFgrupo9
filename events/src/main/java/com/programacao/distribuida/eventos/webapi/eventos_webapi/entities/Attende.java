package com.programacao.distribuida.eventos.webapi.eventos_webapi.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.AttendeInsertDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.entities.common.BaseUser;

@Entity
@DiscriminatorValue("attendees")
public class Attende extends BaseUser {

  private double balance;

  @OneToMany(
    mappedBy = "attende",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<Ticket> tickets;

  public Attende() {

  }

  public Attende(AttendeInsertDTO attende) {
    setName(attende.getName());
    setEmail(attende.getEmail());
    setBalance(0);
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public List<Ticket> getTickets() {
    return tickets;
  }

  public void setTickets(List<Ticket> tickets) {
    this.tickets = tickets;
  }  
}
