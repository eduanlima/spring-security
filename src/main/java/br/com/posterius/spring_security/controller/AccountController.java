package br.com.posterius.spring_security.controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.posterius.spring_security.dto.CreateAccountDTO;
import br.com.posterius.spring_security.entities.Account;
import br.com.posterius.spring_security.entities.Role;
import br.com.posterius.spring_security.repositories.AccountRepository;
import br.com.posterius.spring_security.repositories.RoleRepository;

@RestController
@RequestMapping(value = "/account")
public class AccountController {
	private final AccountRepository accountRepository;
	private final RoleRepository roleRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public AccountController(AccountRepository accountRepository, RoleRepository roleRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.accountRepository = accountRepository;
		this.roleRepository = roleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Transactional
	@PostMapping
	public ResponseEntity<Void> createAccount(@RequestBody CreateAccountDTO dto) {
		var basicRole = roleRepository.findByName(Role.Values.BASIC.name());

		var accountFromDb = accountRepository.findByName(dto.userName());
		if (accountFromDb.isPresent())
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);

		var account = new Account(dto.userName(), bCryptPasswordEncoder.encode(dto.password()), Set.of(basicRole));
		accountRepository.save(account);

		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	public ResponseEntity<List<Account>>listAccount() {
		var accounts = accountRepository.findAll();
		return ResponseEntity.ok(accounts);
	}
}
