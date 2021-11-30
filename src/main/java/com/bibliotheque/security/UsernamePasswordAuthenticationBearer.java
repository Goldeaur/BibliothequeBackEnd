package com.bibliotheque.security;


import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This converter takes a SignedJWT and extracts all information
 * contained to build an Authentication Object
 * The signed JWT has already been verified.
 *
 */
public class UsernamePasswordAuthenticationBearer {

	private UsernamePasswordAuthenticationBearer() {
		super();
	}

	public static Mono<Authentication> create(
			SignedJWT signedJWT
	) {
		String subject = null;
		String auths = null;

		try {
			subject = signedJWT.getJWTClaimsSet().getSubject();
			auths = (String) signedJWT.getJWTClaimsSet().getClaim("roles");
		} catch (ParseException e) {
			return Mono.empty();
		}
		List<SimpleGrantedAuthority> authorities = Stream.of(auths.split(",")).map(a -> new SimpleGrantedAuthority(a)).collect(Collectors.toList());
		return  Mono.justOrEmpty(new UsernamePasswordAuthenticationToken(subject, null, authorities));
	}
}
