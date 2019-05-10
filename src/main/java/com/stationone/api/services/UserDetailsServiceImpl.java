package com.stationone.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stationone.api.entities.Empresa;
import com.stationone.api.repositories.EmpresaRepository;
import com.stationone.api.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private EmpresaRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Empresa empresa = repo.findByEmail(email);
		if(empresa == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return new UserSS(empresa.getId(), empresa.getEmail(), empresa.getSenha(), empresa.getPerfis());
	}

}
