package com.reserva.repository;

import com.reserva.models.Evento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends CrudRepository<Evento, String> {

    Evento findByCodigo(long codigo);
}
