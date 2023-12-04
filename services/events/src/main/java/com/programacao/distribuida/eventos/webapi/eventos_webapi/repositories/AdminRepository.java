package com.programacao.distribuida.eventos.webapi.eventos_webapi.repositories;

import com.programacao.distribuida.eventos.webapi.eventos_webapi.entities.Admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>, JpaSpecificationExecutor<Admin> {
  
  @Nullable
  Admin findByEmail(@Nullable String email);   

}
