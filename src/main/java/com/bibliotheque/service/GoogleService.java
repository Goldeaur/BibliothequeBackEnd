package com.bibliotheque.service;

import com.bibliotheque.exception.MalformedRequestException;
import com.bibliotheque.model.dto.BookRequest;
import com.bibliotheque.model.dto.BookResponse;
import com.bibliotheque.model.dto.googleBook.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class GoogleService {

    String key = "AIzaSyBHlroHFBr8Fs8D3QeHS_JJjvR-8zPwaNE";

    public Flux<BookResponse> askGoogle(BookRequest googleRequest) {
        if(googleRequest.title() == null || googleRequest.author() == null){
           return Flux.error(new MalformedRequestException("Author and title are required to ask google"));
        }
        RestTemplate restTemplate = new RestTemplate();
        var title = formatTitle(googleRequest.title());
        var author = formatAuthor(googleRequest.author());
        var response = restTemplate.getForEntity(
                "https://www.googleapis.com/books/v1/volumes?q=" + title + "+inauthor:" + author + "&key=" + key, GoogleBooksResponse.class);
        GoogleBooksResponse googleResponse = Objects.requireNonNull(response.getBody());
        var future = new CompletableFuture<List<BookResponse>>();
        future.completeAsync(() -> translateIntoBookResponse(googleResponse, googleRequest));
        return Mono.fromFuture(future).flatMapIterable(list -> list);
    }

    private String formatTitle(String title) {
        return title
                .replace("'", "+")
                .replace("-", "+")
                .replace(" ", "+")
                .replace("?", "")
                .replace(",", "")
                .replace("°", "");
    }

    private String formatAuthor(String author) {
        return author.replace(" ", "+");
    }


    private List<BookResponse> translateIntoBookResponse(GoogleBooksResponse googleResponse, BookRequest googleRequest) {
        List<Item> items = Arrays.asList(googleResponse.getItems());
        if (items.isEmpty()) {
            return List.of();
        }
        Iterator<Item> iterator = items.iterator();
        List<BookResponse> responses = new ArrayList<>();

        while (iterator.hasNext()) {
            Item item = iterator.next();
            var bookResponse = BookResponse.builder()
                    .isbn10(getIsbn10(item))
                    .isbn13(getIsbn13(item))
                    .imageLink(getImageLink(item))
                    .title(item.volumeInfo.title)
                    .author(getAuthor(item))
                    .epoch(googleRequest.epoch())
                    .nationality(googleRequest.nationality())
                    .type(googleRequest.type())
                    .subType(getSubTypes(item, googleRequest))
                    .readerCategory(googleRequest.readerCategory())
                    .comment(googleRequest.comment())
                    .description(item.volumeInfo.getDescription())
                    .refBibli(googleRequest.refBibli())
                    .build();
            responses.add(bookResponse);
        }
        return responses;
    }

    private String getSubTypes(Item item, BookRequest request) {
        String[] subCategories = item.volumeInfo.categories;
        if (subCategories != null && subCategories.length > 0)
            return String.join(" ; ", subCategories);
        return request.subType();
    }

    private String getAuthor(Item item) {
        Optional<String> author = Arrays.stream(item.volumeInfo.authors).findFirst();
        return author.isEmpty() ? "" : author.get();
    }

    private String getImageLink(Item item) {
        ImageLinks links = item.volumeInfo.imageLinks;
        if(links == null){
            return "";
        }
        return links.thumbnail.isEmpty() ? links.smallThumbnail : links.thumbnail;
    }

    private String getIsbn10(Item item) {
        IndustryIdentifier[] industryIdentifiers = item.volumeInfo.industryIdentifiers;
        for (IndustryIdentifier industryIdentifier : industryIdentifiers) {
            if (industryIdentifier.type.equals(ISBNTypes.ISBN_10.name())) {
                return industryIdentifier.identifier;
            }
        }
        return "";
    }

    private String getIsbn13(Item item) {
        IndustryIdentifier[] industryIdentifiers = item.volumeInfo.industryIdentifiers;
        for (IndustryIdentifier industryIdentifier : industryIdentifiers) {
            if (industryIdentifier.type.equals(ISBNTypes.ISBN_13.name())) {
                return industryIdentifier.identifier;
            }
        }
        return "";
    }
}

