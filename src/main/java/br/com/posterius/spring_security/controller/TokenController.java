package br.com.posterius.spring_security.controller;

import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public TokenController(JwtEncoder jwtEncoder, AccountRepository accountRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.jwtEncoder = jwtEncoder;
		this.accountRepository = accountRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
		var account = accountRepository.findByAccountName(loginRequest.userName());
		
		if (account.isEmpty() || account.get().isLoginCorrect(loginRequest, bCryptPasswordEncoder))
			throw new BadCredentialsException("User or password is invalid.");
		
		var now = Instant.now();
		var expiresIn = 300L;
		
		
		
		return null;
	}
}
