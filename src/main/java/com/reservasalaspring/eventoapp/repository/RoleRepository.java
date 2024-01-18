package com.reservasalaspring.eventoapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.reservasalaspring.eventoapp.models.Roles;

public interface RoleRepository extends CrudRepository <Roles, Long>{

}
