package br.com.posterius.spring_security.dto;

import java.util.List;

public record FeedDto(List<FeedItemDto> feedItens, int pages, int pageSize, int totalPages, int totalElements) {

}
