package com.programacao.distribuida.eventos.webapi.eventos_webapi.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.EventDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.EventInsertDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.EventUpdateDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.entities.Admin;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.entities.Event;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.entities.Ticket;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.repositories.EventRepository;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.specifications.EventSearchCriteria;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.specifications.EventSpecification;

@Service
public class EventService {

  @Autowired
  private EventRepository repository;

  @Autowired
  private AdminService adminService;

  public EventRepository getRepository() {
    return repository;
  }
  
  public Page<Event> getEvents(
    Optional<String> pageString, 
    Optional<String> limitString,
    Optional<String> name,
    Optional<String> place,
    Optional<String> description,
    Optional<String> emailContact,
    Optional<LocalDate> startDate
  ) {
    int page = Math.max(Integer.parseInt(pageString.orElse("0")), 0);
    int limit = Math.min(Math.max(Integer.parseInt(limitString.orElse("5")), 5), 2000);

    Specification<Event> spec = null;

    if (!name.isEmpty()) {
      spec = EventSpecification.add(spec, new EventSearchCriteria("name", ":", name.get()));
    }

    if (!place.isEmpty()) {
      spec = EventSpecification.add(spec, new EventSearchCriteria("place", ":", place.get()));
    }

    if (!description.isEmpty()) {
      spec = EventSpecification.add(spec, new EventSearchCriteria("description", ":", description.get()));
    }

    if (!emailContact.isEmpty()) {
      spec = EventSpecification.add(spec, new EventSearchCriteria("emailContact", ":", emailContact.get()));
    }

    if (!startDate.isEmpty()) {
      spec = EventSpecification.add(spec, new EventSearchCriteria("startDate", ":", startDate.get()));
    }

    return repository.findAll(spec, PageRequest.of(page, limit, Sort.by("id")));
  }

  public EventDTO getEvent(Long eventId) {
    return new EventDTO(getEventEntity(eventId));
  }

  public Event getEventEntity(Long eventId) {
    return repository.findById(eventId).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "O evento com essa identificação não foi encontrado."));
  }

  public EventDTO createEvent(EventInsertDTO dto) {
    Event entity = new Event(dto);

    if (entity.getStartDate().isAfter(entity.getEndDate()))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O evento não pode ser cadastrado com uma data de início maior que a data final.");

    if (entity.getStartTime().isAfter(entity.getEndTime()))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O evento não pode ser cadastrado com o horário de início maior que o horário final.");

    Admin admin = adminService.getAdminEntity(dto.getAdminId());

    entity.setAdmin(admin);
    entity = repository.save(entity);

    return new EventDTO(entity);
  }

  public EventDTO updateEvent(Long eventId, EventUpdateDTO dto) {
    Event entity = repository.findById(eventId).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "O evento com essa identificação não foi encontrado."));

    if (LocalDate.now().isAfter(entity.getStartDate()) || (LocalDate.now().equals(entity.getStartDate()) && LocalTime.now().isAfter(entity.getStartTime())))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Você não pode atualizar as informações de um evento que já ocorreu.");

    entity.setName(dto.getName());
    entity.setDescription(dto.getDescription());
    entity.setEmailContact(dto.getEmailContact());
    entity.setStartDate(dto.getStartDate());
    entity.setEndDate(dto.getEndDate());
    entity.setStartTime(dto.getStartTime());
    entity.setEndTime(dto.getEndTime());
    entity.setAmountFreeTickets(dto.getAmountFreeTickets());
    entity.setAmountPayedTickets(dto.getAmountPayedTickets());
    entity.setPriceTicket(dto.getPriceTicket());

    if (entity.getStartDate().isAfter(entity.getEndDate()))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O evento não pode ser cadastrado com uma data de início maior que a data final.");

    if (entity.getStartTime().isAfter(entity.getEndTime()))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O evento não pode ser cadastrado com o horário de início maior que o horário final.");

    Admin admin = adminService.getAdminEntity(dto.getAdminId());

    entity.setAdmin(admin);
    entity = repository.save(entity);

    return new EventDTO(entity);
  }

  public void deleteEvent(Long eventId) {
    Event event = getEventEntity(eventId);

    List<Ticket> tickets = event.getTickets();

    if (tickets.size() > 0)
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O evento não pode ser removido porque já foram vendidos um ou mais ingressos para esse evento.");

    try {
      repository.deleteById(eventId);
    } catch (EmptyResultDataAccessException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O evento com essa identificação não foi encontrado.");
    }
  }

  public List<EventDTO> toDTOList(List<Event> list) {
    List<EventDTO> listDTO = new ArrayList<>();

    for (Event event : list) {
      EventDTO dto = new EventDTO(event, true);

      listDTO.add(dto);
    }

    return listDTO;
  }
}
