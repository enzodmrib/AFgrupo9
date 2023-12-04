package com.programacao.distribuida.eventos.webapi.eventos_webapi.dto;

public class TicketInsertDTO {

  private Long attendeId;

  private TicketType type;

  public Long getAttendeId() {
    return attendeId;
  }

  public void setAttendeId(Long attendeId) {
    this.attendeId = attendeId;
  }

  public TicketType getType() {
    return type;
  }

  public void setType(TicketType type) {
    this.type = type;
  }
}