package br.com.posterius.spring_security.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.posterius.spring_security.entities.Tweet;
import br.com.posterius.spring_security.repositories.AccountRepository;
import br.com.posterius.spring_security.repositories.TweetRepository;

@RestController
@RequestMapping("/tweet")
public class TweetController {
	private final TweetRepository tweetRepository;
	private final AccountRepository accountRepository;

	public TweetController(TweetRepository tweetRepository, AccountRepository accountRepository) {
		this.tweetRepository = tweetRepository;
		this.accountRepository = accountRepository;
	}
	
	@PostMapping
	public ResponseEntity<Void> createTweet(@RequestBody CreateTweetDto dto, JwtAuthenticationToken token) {
		var account = accountRepository.findById(UUID.fromString(token.getName()));
		
		var tweet = new Tweet(account.get(), dto.content());
		tweetRepository.save(tweet);
		
		return ResponseEntity.ok().build();
	}
	
}
