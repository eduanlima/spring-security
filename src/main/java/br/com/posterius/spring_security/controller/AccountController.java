package br.com.posterius.spring_security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.posterius.spring_security.entities.Role;
import br.com.posterius.spring_security.repositories.AccountRepository;
import br.com.posterius.spring_security.repositories.RoleRepository;

@RestController
@RequestMapping(value = "/account")
public class AccountController {
	private final AccountRepository accountRepository;
	private final RoleRepository roleRepository;

	public AccountController(AccountRepository accountRepository, RoleRepository roleRepository) {
		this.accountRepository = accountRepository;
		this.roleRepository = roleRepository;
	}
	
	@PostMapping
	public ResponseEntity<Void> createAccount(@ResquestBody CreateAccountDTO cdto){
		var basicRole = roleRepository.findByName(Role.Values.BASIC.name());
		
		return null;
	}
}
