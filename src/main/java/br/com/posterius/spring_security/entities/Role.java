package br.com.posterius.spring_security.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;

@Data
@Entity
@Table(name = "role")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roleId;
	private String name;
	
	public enum Values{
		ADMIN(1L),
		BASIC(2L);
		
		@Getter
		long roleId;
		
		Values(long roleId){
			this.roleId = roleId;
		}
	}
}
