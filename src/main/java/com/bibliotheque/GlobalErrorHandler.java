package com.bibliotheque;

import com.bibliotheque.exceptions.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

@Service
@Order(-2)
@Slf4j
public class GlobalErrorHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper mapper;
    private final DataBufferWriter bufferWriter;

    public GlobalErrorHandler(ObjectMapper mapper, DataBufferWriter bufferWriter) {
        this.mapper = mapper;
        this.bufferWriter = bufferWriter;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {
       // DataBufferFactory bufferFactory = serverWebExchange.getResponse().bufferFactory();
        log.error("ERROR ON PATH : " + serverWebExchange.getRequest().getURI().toString());
        ServerHttpResponse response = serverWebExchange.getResponse();
        HttpStatus status;


        if (throwable instanceof ResourceNotFoundException || throwable instanceof NoSuchElementException) {
            status = HttpStatus.NOT_FOUND;
        } else if (throwable instanceof MalformedRequestException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (throwable instanceof ResourceAlreadyExistsException) {
            status = HttpStatus.CONFLICT;
        } else if (throwable instanceof UnauthorizedRequestException) {
            status = HttpStatus.UNAUTHORIZED;
        } else if (throwable instanceof MethodNotAllowedException) {
            status = HttpStatus.METHOD_NOT_ALLOWED;
        } else if (throwable instanceof HttpResponseErrorException) {
            HttpResponseErrorException exception = (HttpResponseErrorException) throwable;
            try {
                status = HttpStatus.valueOf(exception.getStatusCode());
            } catch (IllegalArgumentException e) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else if (throwable instanceof ResponseStatusException) {
            status = ((ResponseStatusException) throwable).getStatus();
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }


        ErrorMessage errorMessage = ErrorMessage.builder().timestamp(String.valueOf(System.currentTimeMillis()))
                .path(serverWebExchange.getRequest().getPath().value())
                .timestamp(ZonedDateTime.now().toString())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(throwable.getMessage())
                .errorClass(throwable.getClass().getSimpleName())
                .build();
        log.error(throwable.getMessage());
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String responseBody = bufferWriter.createString(errorMessage);

        return bufferWriter.write(response, responseBody);
    }
}