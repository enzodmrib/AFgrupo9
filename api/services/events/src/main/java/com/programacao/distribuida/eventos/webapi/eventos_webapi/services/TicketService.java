package com.programacao.distribuida.eventos.webapi.eventos_webapi.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.EventDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.TicketInsertDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.TicketType;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.entities.Attende;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.entities.Event;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.entities.Ticket;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.repositories.TicketRepository;

@Service
public class TicketService {

  @Autowired
  private AttendeService attendeService;

  @Autowired
  private EventService eventService;

  @Autowired
  private TicketRepository repository;

  public void sellTicket(Long eventId, TicketInsertDTO dto) {
    Event event = eventService.getEventEntity(eventId);
    Attende attende = attendeService.getAttendeeEntity(dto.getAttendeId());

    Ticket ticket = new Ticket(dto);

    if (event.getEndDate().isBefore(LocalDate.now()) || (event.getEndDate().equals(LocalDate.now()) && event.getEndTime().isBefore(LocalTime.now())))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Você não pode comprar um ingresso para um evento que ocorreu no passado.");

    if (dto.getType() == TicketType.FREE) {
      Long amountFreeTickets = event.getAmountFreeTickets();
      Long freeTicketsSelled = event.getFreeTicketsSelled();

      if (freeTicketsSelled >= amountFreeTickets)
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Não há mais ingressos gratuítos disponíveis para esse evento para ser vendido.");

      ticket.setPrice(0);
      event.setFreeTicketsSelled(freeTicketsSelled + 1);
    } else if (dto.getType() == TicketType.PAID) {
      Long amountPaidTickets = event.getAmountPayedTickets();
      Long paidTicketsSelled = event.getPayedTicketsSelled();

      if (paidTicketsSelled >= amountPaidTickets)
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Não há mais ingressos pagos disponíveis para esse evento para ser vendido.");

      ticket.setPrice(event.getPriceTicket());
      attende.setBalance(attende.getBalance() - event.getPriceTicket());

      event.setPayedTicketsSelled(paidTicketsSelled + 1);
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não há como vender esse tipo de ingresso.");
    }

    ticket.setAttende(attende);
    ticket.setEvent(event);

    repository.save(ticket);

    eventService.getRepository().save(event);
    attendeService.getRepository().save(attende);
  }

  public void removeTicket(Long eventId, TicketInsertDTO dto) {
    List<Ticket> tickets = repository.findByAttendeIdAndEventId(dto.getAttendeId(), eventId);

    if (CollectionUtils.isEmpty(tickets)){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi encontrado nenhum ingresso para esse usuário para esse evento.");
    }

    Ticket ticket = tickets.get(0);

    Event event = ticket.getEvent();
    Attende attende = ticket.getAttende();

    if (LocalDate.now().isAfter(event.getStartDate()) || (LocalDate.now().equals(event.getStartDate()) && LocalTime.now().isAfter(event.getStartTime())))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Você não pode vender um ingresso de um evento que já ocorreu ou está ocorrendo agora.");

    if (ticket.getType() == TicketType.FREE) {
      Long freeTicketsSelled = event.getFreeTicketsSelled();

      event.setFreeTicketsSelled(freeTicketsSelled - 1);
    } else if (ticket.getType() == TicketType.PAID) {
      Long paidTicketsSelled = event.getPayedTicketsSelled();

      event.setPayedTicketsSelled(paidTicketsSelled - 1);

      attende.setBalance(attende.getBalance() + event.getPriceTicket());
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não há como remover esse tipo de ingresso.");
    }

    eventService.getRepository().save(event);
    attendeService.getRepository().save(attende);

    repository.delete(ticket);
  }

  public EventDTO getEventAndTickets(Long eventId) {
    Event event = eventService.getEventEntity(eventId);

    return new EventDTO(event, true, true);
  }
}
