package com.programacao.distribuida.eventos.webapi.eventos_webapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.AttendeDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.AttendeInsertDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.AttendeUpdateDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.entities.Attende;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.repositories.AttendeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AttendeService {

  @Autowired
  private AttendeRepository repository;

  public AttendeRepository getRepository() {
    return repository;
  }

  public Page<Attende> getAttendees(Optional<String> pageString, Optional<String> limitString) {
    int page = Math.max(Integer.parseInt(pageString.orElse("0")), 0);
    int limit = Math.min(Math.max(Integer.parseInt(limitString.orElse("5")), 5), 2000);

    return repository.findAll(PageRequest.of(page, limit));
  }

  public AttendeDTO getAttendee(Long attendeId) {
    return new AttendeDTO(getAttendeeEntity(attendeId));
  }

  public Attende getAttendeeEntity(Long attendeId) {
    return repository.findById(attendeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
        "O Convidado com essa identificação não foi encontrado."));
  }

  public Attende getAttendeesEntity(Long attendeId) {
    Attende entity = repository.findById(attendeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
        "O Convidado com essa identificação não foi encontrado."));

    return entity;
  }

  public AttendeDTO createAttendees(AttendeInsertDTO dto) {
    Attende adminWithThatEmail = repository.findByEmail(dto.getEmail());

    if (adminWithThatEmail != null)
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um outro convidado com esse mesmo e-mail.");

    Attende entity = new Attende(dto);

    entity = repository.save(entity);

    return new AttendeDTO(entity);
  }

  public AttendeDTO updateAttendees(Long attendeId, AttendeUpdateDTO dto) {
    Attende entity = repository.findById(attendeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
        "O convidado com essa identificação não foi encontrado."));

    Attende attendeWithThatEmail = repository.findByEmail(dto.getEmail());

    if (attendeWithThatEmail != null && attendeWithThatEmail.getId() != attendeId)
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um outro convidado com esse mesmo e-mail.");

    entity.setName(dto.getName());
    entity.setEmail(dto.getEmail());

    entity = repository.save(entity);

    return new AttendeDTO(entity);
  }

  public void deleteAttendees(Long attendeId) {
    try {
      repository.deleteById(attendeId);
    } catch (EmptyResultDataAccessException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O convidado com essa identificação não foi encontrado.");
    }
  }

  public List<AttendeDTO> toDTOList(List<Attende> list) {
    List<AttendeDTO> listDTO = new ArrayList<>();

    for (Attende Admin : list) {
      AttendeDTO dto = new AttendeDTO(Admin, true);

      listDTO.add(dto);
    }

    return listDTO;
  }
}
