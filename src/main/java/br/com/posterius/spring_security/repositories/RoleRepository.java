package br.com.posterius.spring_security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.posterius.spring_security.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
