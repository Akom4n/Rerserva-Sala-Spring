package com.reservasalaspring.eventoapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.reservasalaspring.eventoapp.models.Convidado;
import com.reservasalaspring.eventoapp.models.Evento;

@Repository
public interface ConvidadoRepository extends CrudRepository <Convidado, String>{
	Iterable<Convidado> findByEvento(Evento evento);
	Convidado findByRg(String rg);
}
