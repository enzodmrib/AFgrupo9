package com.programacao.distribuida.eventos.webapi.eventos_webapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.AdminDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.AdminInsertDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.dto.AdminUpdateDTO;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.entities.Admin;
import com.programacao.distribuida.eventos.webapi.eventos_webapi.repositories.AdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AdminService {

  @Autowired
  private AdminRepository repository;

  public Page<Admin> getAdmins(Optional<String> pageString, Optional<String> limitString) {
    int page = Math.max(Integer.parseInt(pageString.orElse("0")), 0);
    int limit = Math.min(Math.max(Integer.parseInt(limitString.orElse("5")), 5), 2000);

    return repository.findAll(PageRequest.of(page, limit));
  }

  public AdminDTO getAdmin(Long adminId) {
    Admin entity = repository.findById(adminId).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "O Administrador com essa identificação não foi encontrado."));
    
    return new AdminDTO(entity);
  }

  public Admin getAdminEntity(Long adminId) {
    Admin entity = repository.findById(adminId).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "O Administrador com essa identificação não foi encontrado."));
    
    return entity;
  }

  public AdminDTO createAdmin(AdminInsertDTO dto) {
    Admin adminWithThatEmail = repository.findByEmail(dto.getEmail());

    if (adminWithThatEmail != null)
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um outro administrador com esse mesmo e-mail.");

    Admin entity = new Admin(dto);

    entity = repository.save(entity);

    return new AdminDTO(entity);
  }

  public AdminDTO updateAdmin(Long adminId, AdminUpdateDTO dto) {
    Admin entity = repository.findById(adminId).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "O Administrador com essa identificação não foi encontrado."));

    Admin adminWithThatEmail = repository.findByEmail(dto.getEmail());

    if (adminWithThatEmail != null && adminWithThatEmail.getId() != adminId)
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um outro administrador com esse mesmo e-mail.");

    entity.setName(dto.getName());
    entity.setEmail(dto.getEmail());
    entity.setPhoneNumber(dto.getPhoneNumber());

    entity = repository.save(entity);

    return new AdminDTO(entity);
  }

  public void deleteAdmin(Long adminId) {
    try {
      repository.deleteById(adminId);
    } catch (EmptyResultDataAccessException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O Administrador com essa identificação não foi encontrado.");
    }
  }

  public List<AdminDTO> toDTOList(List<Admin> list) {
    List<AdminDTO> listDTO = new ArrayList<>();

    for (Admin Admin : list) {
      AdminDTO dto = new AdminDTO(Admin, true);

      listDTO.add(dto);
    }

    return listDTO;
  }
}
