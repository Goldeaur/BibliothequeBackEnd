package com.bibliotheque.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * This converter extracts a bearer token from a WebExchange and
 * returns an Authentication object if the JWT token is valid.
 * Validity means is well formed and tocken is not expired
 */

@Component
public class ServerHttpBearerAuthenticationConverter implements ServerAuthenticationConverter {

    private static final String BEARER = "Bearer ";
    private static final Predicate<String> matchBearerLength = authValue -> authValue.length() > BEARER.length();
    private static final Function<String,Mono<String>> isolateBearerValue = authValue -> Mono.justOrEmpty(authValue.substring(BEARER.length()));

    @Autowired
    private  JWTVerifier jwtVerifier;
  
	@Override
	public Mono<Authentication> convert(ServerWebExchange exchange) {
		return Mono.justOrEmpty(exchange)
        .flatMap(AuthorizationHeaderPayload::extract)
           .filter(matchBearerLength)
        .flatMap(isolateBearerValue)
        .flatMap(jwtVerifier::check)
        .flatMap(UsernamePasswordAuthenticationBearer::create).log();
	}
}
