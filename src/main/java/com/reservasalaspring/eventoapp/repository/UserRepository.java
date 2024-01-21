package com.reservasalaspring.eventoapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.reservasalaspring.eventoapp.models.User;

public interface UserRepository extends CrudRepository <User, Long>{
	User fundByUsername(String username);

	tech.den.Security.user.User findbyUsername(Object username);
}
