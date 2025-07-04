package br.com.posterius.spring_security.entities;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tweet")
public class Tweet {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long tweetId;
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
	private String content;
	@CreationTimestamp
	private Instant creantionDate;
	
	public Tweet(Account account, String content) {
		this.account = account;
		this.content = content;
	}
}
