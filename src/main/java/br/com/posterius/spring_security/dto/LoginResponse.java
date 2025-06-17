package br.com.posterius.spring_security.dto;

public record LoginResponse(String accessToken, Long expireIn) {

}
