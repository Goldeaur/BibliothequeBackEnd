
package com.bibliotheque.security;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * An authentication manager intended to authenticate a JWT exchange
 * JWT tokens contain all information within the token itself.
 * Invalid tokens are filtered on previous step
 */

@Component
public class BearerTokenReactiveAuthenticationManager implements ReactiveAuthenticationManager {

 
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication);
    }
}
