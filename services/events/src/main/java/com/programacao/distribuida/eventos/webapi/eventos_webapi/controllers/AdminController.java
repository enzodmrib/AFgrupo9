package com.programacao.distribuida.eventos.webapi.eventos_webapi.controllers;

import java.util.List;
import java.util.Optional;

import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.AdminDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.AdminInsertDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.AdminUpdateDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.entities.Admin;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.services.AdminService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admins")
@Tag(name = "Admin Controller")
public class AdminController {

  @Autowired
  private AdminService service;

  @GetMapping
  public ResponseEntity<List<AdminDTO>> getAdmins(
      @RequestParam(name = "page", required = false, defaultValue = "0") Optional<String> page,
      @RequestParam(name = "limit", required = false, defaultValue = "5") Optional<String> limit) {
    Page<Admin> pageable = service.getAdmins(page, limit);

    HttpHeaders headers = new HttpHeaders();

    headers.set("X-Pagination-PageCount", String.valueOf(pageable.getNumberOfElements()));
    headers.set("X-Pagination-PageLimit", String.valueOf(pageable.getSize()));
    headers.set("X-Pagination-Page", String.valueOf(pageable.getPageable().getPageNumber()));
    headers.set("X-Pagination-TotalPage", String.valueOf(pageable.getTotalPages()));
    headers.set("X-Pagination-TotalElements", String.valueOf(pageable.getTotalElements()));

    return ResponseEntity.ok().headers(headers).body(service.toDTOList(pageable.getContent()));
  }

  @PostMapping
  public ResponseEntity<AdminDTO> createAdmin(@RequestBody AdminInsertDTO dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.createAdmin(dto));
  }

  @GetMapping("{adminId}")
  public ResponseEntity<AdminDTO> getAdmin(@PathVariable Long adminId) {
    return ResponseEntity.ok(service.getAdmin(adminId));
  }

  @PutMapping("{adminId}")
  public ResponseEntity<AdminDTO> updateAdmin(@PathVariable Long adminId, @RequestBody AdminUpdateDTO dto) {
    return ResponseEntity.ok(service.updateAdmin(adminId, dto));
  }

  @DeleteMapping("{adminId}")
  public ResponseEntity<Void> deleteAdmin(@PathVariable Long adminId) {
    service.deleteAdmin(adminId);

    return ResponseEntity.noContent().build();
  }
}
