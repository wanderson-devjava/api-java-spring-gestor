package com.stationone.api;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.stationone.api.entities.Empresa;
import com.stationone.api.repositories.EmpresaRepository;

@SpringBootApplication
public class StationOneApplication implements CommandLineRunner{
	
	@Autowired
	private EmpresaRepository empresaRepositore;

	public static void main(String[] args) {
		SpringApplication.run(StationOneApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Empresa empresa1 = new Empresa(null, "PSMA", "Paroqui São Miguel Arcanjo", "12345679");
		
		empresaRepositore.saveAll(Arrays.asList(empresa1));
		
	}

}
