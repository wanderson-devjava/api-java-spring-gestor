package com.stationone.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stationone.api.entities.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

}
