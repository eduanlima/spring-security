package br.com.posterius.spring_security.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.posterius.spring_security.repositories.AccountRepository;
import br.com.posterius.spring_security.repositories.RoleRepository;

@Configuration
public class AdminConfig implements CommandLineRunner {
	private RoleRepository roleRepository;
	private AccountRepository accountRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	
	public AdminConfig(RoleRepository roleRepository, AccountRepository accountRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.roleRepository = roleRepository;
		this.accountRepository = accountRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
