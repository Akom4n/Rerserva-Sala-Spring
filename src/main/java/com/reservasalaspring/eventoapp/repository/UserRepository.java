package com.reservasalaspring.eventoapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.reservasalaspring.eventoapp.models.Users;

public interface UserRepository extends CrudRepository <Users, Long>{

}
