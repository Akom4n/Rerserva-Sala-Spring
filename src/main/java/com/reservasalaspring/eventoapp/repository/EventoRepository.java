package com.reservasalaspring.eventoapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.reservasalaspring.eventoapp.models.Evento;

@Repository
public interface EventoRepository extends CrudRepository <Evento, Long>{
	Evento findByCodigo(long codigo);
}
