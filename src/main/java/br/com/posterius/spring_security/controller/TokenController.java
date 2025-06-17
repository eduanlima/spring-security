package br.com.posterius.spring_security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.posterius.spring_security.dto.LoginRequest;
import br.com.posterius.spring_security.dto.LoginResponse;
import br.com.posterius.spring_security.repositories.AccountRepository;

@RestController
public class TokenController {
	private final JwtEncoder jwtEncoder;
	private final AccountRepository accountRepository;
	
	public TokenController(JwtEncoder jwtEncoder, AccountRepository accountRepository) {
		this.jwtEncoder = jwtEncoder;
		this.accountRepository = accountRepository;
	}
}
