package com.stationone.api.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.stationone.api.dto.EmpresaDTO;
import com.stationone.api.dto.EmpresaNewDTO;
import com.stationone.api.entities.Cidade;
import com.stationone.api.entities.Empresa;
import com.stationone.api.entities.Endereco;
import com.stationone.api.repositories.EmpresaRepository;
import com.stationone.api.repositories.EnderecoRepository;
import com.stationone.api.services.exceptions.DataIntegrityException;
import com.stationone.api.services.exceptions.ObjectNotFoundException;

@Service
public class EmpresaService {

	@Autowired
	private EmpresaRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public List<Empresa> findAll(){
		return repo.findAll();
	}
	
	public Empresa find(long id) {
		Optional<Empresa> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Empresa.class.getName()));
	}
	
	@Transactional
	public Empresa insert(Empresa obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Empresa update(Empresa obj) {
		Empresa newOBJ = find(obj.getId());
		updateData(newOBJ, obj);
		return repo.save(newOBJ);
	}
	
	public void delete(Long id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}
	}
	
	private void updateData(Empresa newOBJ, Empresa obj) {
		newOBJ.setRazaoSocial(obj.getRazaoSocial());
		newOBJ.setNomeFantasia(obj.getNomeFantasia());
		newOBJ.setCnpj(obj.getCnpj());
		newOBJ.setEmail(obj.getEmail());
	}
	
	public Empresa fromDTO(EmpresaDTO objDTO) {
		return new Empresa(objDTO.getId(), objDTO.getRazaoSocial(), objDTO.getNomeFantasia(), objDTO.getCnpj(), objDTO.getEmail());
	}
	
	public Empresa fromDTO(EmpresaNewDTO objDTO) {
		Empresa empresa = new Empresa(null, objDTO.getRazaoSocial(), objDTO.getNomeFantasia(), objDTO.getCnpj(), objDTO.getEmail());
		Cidade cidade = new Cidade(objDTO.getCidadeId(), null, null);
		Endereco endereco = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), empresa, cidade);
		
		empresa.getEnderecos().add(endereco);
		empresa.getTelefones().add(objDTO.getTelefone1());
		
		if (objDTO.getTelefone2()!=null) {
			empresa.getTelefones().add(objDTO.getTelefone2());
		}
		if (objDTO.getTelefone3()!=null) {
			empresa.getTelefones().add(objDTO.getTelefone3());
		}
		
		return empresa;
	}
	
	public Page<Empresa> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
}
