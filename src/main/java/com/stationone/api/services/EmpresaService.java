package com.stationone.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stationone.api.entities.Empresa;
import com.stationone.api.repositories.EmpresaRepository;
import com.stationone.api.services.exceptions.ObjectNotFoundException;

@Service
public class EmpresaService {

	@Autowired
	private EmpresaRepository repo;

	public Empresa find(long id) {
		Optional<Empresa> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Empresa.class.getName()));
	}

	public List<Empresa> findAll() {
		return repo.findAll();
	}
	
	public Empresa insert(Empresa obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Empresa update(Empresa obj) {
		find(obj.getId());
		return repo.save(obj);
	}
}
