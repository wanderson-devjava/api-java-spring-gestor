package com.stationone.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stationone.api.entities.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long>{

	@Transactional(readOnly = true)
	Empresa findByCnpj(String cnpj);
	
	@Transactional(readOnly = true)
	Optional<Empresa> findById(Long id);
}
