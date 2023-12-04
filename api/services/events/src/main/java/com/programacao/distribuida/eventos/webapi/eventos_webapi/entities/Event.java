package com.programacao.distribuida.eventos.webapi.eventos_webapi.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.EventInsertDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.entities.enums.EventTypeEnum;

@Entity
@Table(name = "TB_EVENT")
public class Event implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Enumerated(value = EnumType.STRING)
  private EventTypeEnum eventType;

  private String description;

  private String emailContact;

  private LocalDate startDate;

  private LocalDate endDate;

  private LocalTime startTime;

  private LocalTime endTime;

  private Long amountFreeTickets;

  private Long amountPayedTickets;

  private Double priceTicket;

  private Long freeTicketsSelled;

  private Long payedTicketsSelled;

  @ManyToOne(fetch = FetchType.EAGER)
  private Admin admin;

  @OneToMany(
    mappedBy = "event",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<Ticket> tickets;

  @ManyToMany()
  @JoinTable(name = "TB_PLACES_EVENTS", joinColumns = { @JoinColumn(name = "event_id") }, inverseJoinColumns = {
      @JoinColumn(name = "place_id") })
  private List<Place> places = new ArrayList<>();

  public Event() {

  }

  public Event(EventInsertDTO dto) {
    this.name = dto.getName();
    this.eventType = EventTypeEnum.valueOf(dto.getEventType());
    this.description = dto.getDescription();
    this.emailContact = dto.getEmailContact();
    this.startDate = dto.getStartDate();
    this.endDate = dto.getEndDate();
    this.startTime = dto.getStartTime();
    this.endTime = dto.getEndTime();
    this.amountFreeTickets = dto.getAmountFreeTickets();
    this.amountPayedTickets = dto.getAmountPayedTickets();
    this.priceTicket = dto.getPriceTicket();
    this.freeTicketsSelled = 0L;
    this.payedTicketsSelled = 0L;
  }

  /**
   * 
   * name description place start date end date start time end time email contact.
   */

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (obj == null)
      return false;

    if (getClass() != obj.getClass())
      return false;

    Event other = (Event) obj;

    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;

    return true;
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

  public String getEventTypeLabel() {
    return eventType.getLabel();
  }

  public EventTypeEnum getEventType() {
    return eventType;
  }

  public void setEventType(EventTypeEnum eventType) {
    this.eventType = eventType;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getEmailContact() {
    return emailContact;
  }

  public void setEmailContact(String emailContact) {
    this.emailContact = emailContact;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
  }

  public LocalTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalTime endTime) {
    this.endTime = endTime;
  }

  public Long getAmountFreeTickets() {
    return amountFreeTickets;
  }

  public void setAmountFreeTickets(Long amountFreeTickets) {
    this.amountFreeTickets = amountFreeTickets;
  }

  public Long getAmountPayedTickets() {
    return amountPayedTickets;
  }

  public void setAmountPayedTickets(Long amountPayedTickets) {
    this.amountPayedTickets = amountPayedTickets;
  }

  public Double getPriceTicket() {
    return priceTicket;
  }

  public void setPriceTicket(Double priceTicket) {
    this.priceTicket = priceTicket;
  }

  public Long getFreeTicketsSelled() {
    return freeTicketsSelled;
  }

  public void setFreeTicketsSelled(Long freeTicketsSelled) {
    this.freeTicketsSelled = freeTicketsSelled;
  }

  public Long getPayedTicketsSelled() {
    return payedTicketsSelled;
  }

  public void setPayedTicketsSelled(Long payedTicketsSelled) {
    this.payedTicketsSelled = payedTicketsSelled;
  }

  public List<Place> getPlaces() {
    return places;
  }

  public void setPlaces(List<Place> places) {
    this.places = places;
  }

  public Admin getAdmin() {
    return admin;
  }

  public void setAdmin(Admin admin) {
    this.admin = admin;
  }

  public List<Ticket> getTickets() {
    return tickets;
  }

  public void setTickets(List<Ticket> tickets) {
    this.tickets = tickets;
  }

  public boolean intersectsDateWith(Event event) {
    // Caso a data de início ou fim de ambos sejam iguais, deve se considerar o
    // horário
    // Ex: 2020-12-11->2020-12-12 10:00->12:00 e outro em 2020-12-12->2020-12-12
    // 14:00->19:00
    if (startDate.isEqual(event.startDate) || endDate.isEqual(event.endDate) || startDate.isEqual(event.endDate) || endDate.isEqual(event.startDate)) {
      return this.intersectsTimeWith(event);
    }

    // Se a data de início do evento1 está entre a data de início e fim do evento2;
    // startDate BETWEEN startDate AND endDate
    if (startDate.isAfter(event.startDate) && startDate.isBefore(event.endDate)) {
      return this.intersectsTimeWith(event);
    }

    // Se a data de fim do evento1 está entre a data de início e fim do evento2;
    // endDate BETWEEN startDate AND endDate
    if (endDate.isAfter(event.startDate) && endDate.isBefore(event.endDate)) {
      return this.intersectsTimeWith(event);
    }

    // Se a data de início do evento2 está entre a data de início e fim do evento1;
    // startDate BETWEEN startDate AND endDate
    if (event.startDate.isAfter(startDate) && event.startDate.isBefore(endDate)) {
      return this.intersectsTimeWith(event);
    }

    // Se a data de fim do evento2 está entre a data de início e fim do evento1;
    // endDate BETWEEN startDate AND endDate
    if (event.endDate.isAfter(startDate) && event.endDate.isBefore(endDate)) {
      return this.intersectsTimeWith(event);
    }

    return false;
  }

  public boolean intersectsTimeWith(Event event) {
    // Se a hora de início e fim seja igual a hora de inicio e fim do evento2;
    // startTime = startTime AND endTime = endTime
    if (startTime.equals(event.startTime) && endTime.equals(event.endTime))
      return true;

    // Se a hora de início do evento1 está entre a hora de início e fim do evento2;
    // startTime BETWEEN startTime AND endTime
    if (startTime.isAfter(event.startTime) && startTime.isBefore(event.endTime))
      return true;

    // Se a hora de fim do evento 1 está entre a hora de início e fim do evento 2;
    // endTime BETWEEN startTime AND endTime
    if (endTime.isAfter(event.startTime) && endTime.isBefore(event.endTime))
      return true;

    // Se a hora de início do evento2 está entre a hora de início e fim do evento1;
    // startTime BETWEEN startTime AND endTime
    if (event.startTime.isAfter(startTime) && event.startTime.isBefore(endTime))
      return true;

    // Se a hora de fim do evento2 está entre a hora de início e fim do evento1;
    // endTime BETWEEN startTime AND endTime
    if (event.endTime.isAfter(startTime) && event.endTime.isBefore(endTime))
      return true;

    return false;
  }
}
