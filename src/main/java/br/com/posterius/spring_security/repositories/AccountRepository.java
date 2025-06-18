package br.com.posterius.spring_security.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.posterius.spring_security.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID>{
	Optional<Account> findByName(String userName);
}
