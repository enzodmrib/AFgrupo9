package com.programacao.distribuida.eventos.webapi.eventos_webapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.programacao.distribuida.eventos.webapi.eventos_webapi.entities.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    // @Query(nativeQuery = true, value = "SELECT te.* FROM \"tb_places_events\" AS tpe " +
    //                                     "JOIN \"tb_event\" AS te ON tpe.\"event_id\" = te.\"id\" " +
    //                                     "JOIN \"tb_place\" AS tp ON tpe.\"place_id\" = tp.\"id\" " +
    //                                     "WHERE tp.\"city\" = :city ")
    // List<Event> findEventsByCity(@Param("city") String city);

    @Query(nativeQuery = true, value = "SELECT te.* FROM \"tb_places_events\" AS tpe " +
                                        "JOIN \"tb_event\" AS te ON tpe.\"event_id\" = te.\"id\" " +
                                        "JOIN \"tb_place\" AS tp ON tpe.\"place_id\" = tp.\"id\" " +
                                        "WHERE tp.id = :placeId ")
    List<Event> findEventIdsByPlace(@Param("placeId") Long placeId);
}
