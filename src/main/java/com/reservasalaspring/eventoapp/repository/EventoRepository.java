package com.reservasalaspring.eventoapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.reservasalaspring.eventoapp.models.Evento;

public interface EventoRepository extends CrudRepository <Evento, Long>{
	Evento findByCodigo(long codigo);
}
