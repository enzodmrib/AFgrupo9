package com.programacao.distribuida.eventos.webapi.eventos_webapi.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventInsertDTO {

  private String name;

  private String eventType;

  private String description;

  private String place;

  private String emailContact;

  private LocalDate startDate;

  private LocalDate endDate;

  private LocalTime startTime;

  private LocalTime endTime;

  private Long amountFreeTickets;

  private Long amountPayedTickets;

  private Double priceTicket;

  private Long adminId;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPlace() {
    return place;
  }

  public void setPlace(String place) {
    this.place = place;
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

  public Long getAdminId() {
    return adminId;
  }

  public void setAdminId(Long adminId) {
    this.adminId = adminId;
  }
}
