package br.com.posterius.spring_security.controller;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.posterius.spring_security.dto.FeedDto;
import br.com.posterius.spring_security.dto.FeedItemDto;
import br.com.posterius.spring_security.entities.Role;
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
	
	@GetMapping
	private ResponseEntity<FeedDto> feed(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize) {
		var tweets = tweetRepository.findAll(
				PageRequest.of(page, pageSize, Sort.Direction.DESC, "creantionDate"))
				.map(tweet -> new FeedItemDto(tweet.getTweetId(), tweet.getContent(), tweet.getAccount().getName()));
		
		return ResponseEntity.ok(new FeedDto(tweets.getContent(), page, pageSize, tweets.getTotalPages(), tweets.getTotalElements()));
	}

	@PostMapping
	public ResponseEntity<Void> createTweet(@RequestBody CreateTweetDto dto, JwtAuthenticationToken token) {
		var account = accountRepository.findById(UUID.fromString(token.getName()));

		var tweet = new Tweet(account.get(), dto.content());
		tweetRepository.save(tweet);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	private ResponseEntity<Void> delete(@PathVariable Long id, JwtAuthenticationToken token) {
		var tweet = tweetRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		var account = accountRepository.findById(UUID.fromString(token.getName()));
		var isAdmin = account.get().getRoles().stream().anyMatch(role -> role.getName().equalsIgnoreCase(Role.Values.ADMIN.name()));
		
		if (!isAdmin && !tweet.getAccount().getAccountId().equals(UUID.fromString(token.getName())))
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		
		tweetRepository.deleteById(id);
		
		return ResponseEntity.ok().build();
	}
}
