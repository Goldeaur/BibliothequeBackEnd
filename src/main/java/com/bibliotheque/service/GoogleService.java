package com.bibliotheque.service;

import com.bibliotheque.model.dto.BookResponse;
import com.bibliotheque.model.dto.googleBook.GoogleBooksResponse;
import com.bibliotheque.model.dto.googleBook.GoogleRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Objects;

@Service
public class GoogleService {

    String key = "AIzaSyBHlroHFBr8Fs8D3QeHS_JJjvR-8zPwaNE";

    public Mono<GoogleBooksResponse> askGoogle (GoogleRequest googleRequest) {
        RestTemplate restTemplate = new RestTemplate();
        var title = googleRequest.title()
                .replace("'", "+")
                .replace(",", "")
                .replace("-", "+")
                .replace("?", "")
                .replace("°", "")
                .replace(" ", "+");
        var author = googleRequest.author().replace(" ", "+");
        var response = restTemplate.getForEntity(
                "https://www.googleapis.com/books/v1/volumes?q="+title+"+inauthor:"+author+"&key="+key, GoogleBooksResponse.class);
                return Mono.just(Objects.requireNonNull(response.getBody()));
    }

/*    private BookResponse translateIntoBookResponse(GoogleBooksResponse googleResponse, String title){
        var goodItemOptional = Arrays.stream(googleResponse.getItems()).filter(item ->
            item.getVolumeInfo().title.contains(title));
        if(goodItemOptional.isEmpty()){
            return BookResponse.builder().title("ERROR ASKING GOOGLE...").build();
        }
        var goodItem = goodItemOptional.get();
        return BookResponse.builder(goodItem.)
                .description()
                .author()
                .comment()
                .imageLink()
                .isbn10()
                .isbn13()
                .readerCategory()
                .nationality()
                .subType()
                .type()
                .epoch()
                .build();
    }*/
}

