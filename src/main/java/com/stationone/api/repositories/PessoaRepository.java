package com.stationone.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stationone.api.entities.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>  {

	@Transactional(readOnly = true)
	Pessoa findByCpf(String cpf);
	
	@Transactional(readOnly = true)
	Pessoa findByEmail(String email);
	
	@Transactional(readOnly = true)
	Pessoa findByCpfOrEmail(String cpf, String email);
}
