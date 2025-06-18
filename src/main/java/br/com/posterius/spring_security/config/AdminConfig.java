package br.com.posterius.spring_security.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import br.com.posterius.spring_security.entities.Account;
import br.com.posterius.spring_security.entities.Role;
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

	@Transactional
	@Override
	public void run(String... args) throws Exception {
		var roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name());
		var accountAdmin = accountRepository.findByName("admin");
		
		accountAdmin.ifPresentOrElse(a -> {
			System.out.println("Account admin is present.");
		},
		()-> {
			var account = new Account("admin", bCryptPasswordEncoder.encode("123"), Set.of(roleAdmin));
			accountRepository.save(account);
		});
	}

}
