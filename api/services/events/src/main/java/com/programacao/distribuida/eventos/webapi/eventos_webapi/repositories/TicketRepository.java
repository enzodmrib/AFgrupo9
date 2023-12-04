package com.programacao.distribuida.eventos.webapi.eventos_webapi.repositories;

import java.util.List;

import com.programacao.distribuida.eventos.webapi.eventos_webapi.entities.Ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {

  @Nullable
  List<Ticket> findByAttendeIdAndEventId(Long attendeId, Long eventId);

}
