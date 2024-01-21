package com.reservasalaspring.eventoapp.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.reservasalaspring.eventoapp.repository.UserRepository;

import tech.den.Security.user.User;

public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository  userRepository;
	
	@Override
	public User create(User user) {
		User existUser = userRepository.findbyUsername(user.getUsername());
		
		if(existUser != null) {
			throw new Error("user already exists!");
		}
		
		user.setPassword();
		
		return null;
	}
}
