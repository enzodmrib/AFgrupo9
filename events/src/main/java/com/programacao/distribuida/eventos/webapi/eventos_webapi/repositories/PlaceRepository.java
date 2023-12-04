package com.programacao.distribuida.eventos.webapi.eventos_webapi.repositories;

import com.programacao.distribuida.eventos.webapi.eventos_webapi.entities.Place;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PlaceRepository extends JpaRepository<Place, Long>, JpaSpecificationExecutor<Place> {

    List<Place> findByCity(String city);
}
