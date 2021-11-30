
package com.bibliotheque.security;

import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.function.Predicate;

@Component
public class JWTVerifier {
	

    public Mono<SignedJWT> check(String token) {
        return Mono.justOrEmpty(createJWS(token)).filter(isNotExpired);
    }

    private Predicate<SignedJWT> isNotExpired = token -> getExpirationDate(token).after(Date.from(Instant.now()));

    private SignedJWT createJWS(String token) {
        try {
            return SignedJWT.parse(token);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Date getExpirationDate(SignedJWT token) {
        try {
            return token.getJWTClaimsSet().getExpirationTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
