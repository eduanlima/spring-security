package br.com.posterius.spring_security.controller;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.posterius.spring_security.dto.LoginRequest;
import br.com.posterius.spring_security.dto.LoginResponse;
import br.com.posterius.spring_security.entities.Role;
import br.com.posterius.spring_security.repositories.AccountRepository;

@RestController
@RequestMapping("/login")
public class TokenController {
	private final JwtEncoder jwtEncoder;
	private final AccountRepository accountRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public TokenController(JwtEncoder jwtEncoder, AccountRepository accountRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.jwtEncoder = jwtEncoder;
		this.accountRepository = accountRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@PostMapping
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
		var account = accountRepository.findByName(loginRequest.userName());
		
		if (account.isEmpty() || !account.get().isLoginCorrect(loginRequest, bCryptPasswordEncoder))
			throw new BadCredentialsException("User or password is invalid.");
		
		var now = Instant.now();
		var expiresIn = 300L;
		
		var scopes = account.get().getRoles().stream().map(Role::getName).collect(Collectors.toList());
		
		var claims = JwtClaimsSet.builder()
				.issuer("spring_security")
				.subject(account.get().getAccountId().toString())
				.issuedAt(now)
				.expiresAt(now.plusSeconds(expiresIn))
				.claim("scope", scopes)
				.build();
		
		var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
		
		return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
	}
}
