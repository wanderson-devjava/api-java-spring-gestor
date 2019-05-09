package com.stationone.api.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.stationone.api.dto.EmpresaDTO;
import com.stationone.api.dto.EmpresaNewDTO;
import com.stationone.api.entities.Empresa;
import com.stationone.api.services.EmpresaService;

@RestController
@RequestMapping(value = "/empresas")
public class EmpresaResource {
	
	@Autowired
	private EmpresaService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<EmpresaDTO>> findAll(){
		List<Empresa> list = service.findAll();
		List<EmpresaDTO> listDTO = list.stream().map(obj -> new EmpresaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Empresa> find(@PathVariable Long id){
		Empresa obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody EmpresaNewDTO objDTO){
		Empresa obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody EmpresaDTO objDTO, @PathVariable Long id){
		Empresa obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<EmpresaDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Empresa> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<EmpresaDTO> listDTO = list.map(obj -> new EmpresaDTO(obj));  
		return ResponseEntity.ok().body(listDTO);
	}	
	
}
