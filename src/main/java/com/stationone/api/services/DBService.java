package com.stationone.api.services;

import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.stationone.api.entities.Cidade;
import com.stationone.api.entities.Empresa;
import com.stationone.api.entities.Estado;
import com.stationone.api.repositories.CidadeRepository;
import com.stationone.api.repositories.EmpresaRepository;
import com.stationone.api.repositories.EstadoRepository;

@Service
public class DBService {
	
	@Autowired 
	private BCryptPasswordEncoder pe;

	@Autowired
	private EmpresaRepository empresaRepositore;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	public void instantiateTestDatabase() throws ParseException {

		Empresa empresa1 = new Empresa(null, "PSGA", "Paroqui São Gabriel Arcanjo", "12345679", "psga@gmail.com", pe.encode("123456"));
		Empresa empresa2 = new Empresa(null, "PSRA", "Paroqui São Rafael Arcanjo", "12345679", "psra@gmail.com", pe.encode("123456"));
		Empresa empresa3 = new Empresa(null, "PSMA", "Paroqui São Miguel Arcanjo", "12345679", "psma@gmail.com", pe.encode("123456"));

		empresaRepositore.saveAll(Arrays.asList(empresa1, empresa2, empresa3));

		Estado estado = new Estado(null, "Distrito Federal");

		Cidade cidade = new Cidade(null, "Brasilia", estado);

		estado.getCidades().addAll(Arrays.asList(cidade));

		estadoRepository.saveAll(Arrays.asList(estado));
		cidadeRepository.saveAll(Arrays.asList(cidade));
	}
}
