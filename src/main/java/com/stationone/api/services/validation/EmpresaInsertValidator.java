package com.stationone.api.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.stationone.api.dto.EmpresaNewDTO;
import com.stationone.api.entities.Empresa;
import com.stationone.api.repositories.EmpresaRepository;
import com.stationone.api.resources.exception.FieldMessage;

public class EmpresaInsertValidator implements ConstraintValidator<EmpresaInsert, EmpresaNewDTO>{
	
	private EmpresaRepository repo;
	
	@Override
	public void initialize(EmpresaInsert ann) {
	}

	@Override
	public boolean isValid(EmpresaNewDTO objDTO, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		Empresa aux = repo.findByEmail(objDTO.getEmail());
		if (aux != null) {
			list.add(new FieldMessage("email", "Email já existente"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}

}
