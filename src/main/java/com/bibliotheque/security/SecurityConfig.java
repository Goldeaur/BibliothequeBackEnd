package com.bibliotheque.security;


import com.bibliotheque.config.BibliothequeConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
    @Autowired
    private BearerTokenReactiveAuthenticationManager authenticationManager;

    @Autowired
    private ServerHttpBearerAuthenticationConverter serverHttpBearerAuthenticationConverter;

    @Autowired
    private BibliothequeConfigProperties bibliothequeConfigProperties;


    @Autowired
    private  SecurityContextRepository securityContextRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /*
     *  /**
     * A custom UserDetailsService to provide quick user rights for Spring Security,
     * more formal implementations may be added as separated files and annotated as
     * a Spring stereotype.
     *
     * @return MapReactiveUserDetailsService an InMemory implementation of user details
     */
    @Bean
    public MapReactiveUserDetailsService userDetailsRepository() {
        UserDetails user = User.withUsername(bibliothequeConfigProperties.getUserActuator())
                .password(passwordEncoder().encode(bibliothequeConfigProperties.getPasswordActuator()))
                .roles("ADMIN")
                .build();
        return new MapReactiveUserDetailsService(user);
    }

    /**
     *
     * @return
     */

    private AuthenticationWebFilter bearerAuthenticationFilter(){
        AuthenticationWebFilter bearerAuthenticationFilter = new AuthenticationWebFilter(authenticationManager);
        bearerAuthenticationFilter.setServerAuthenticationConverter(serverHttpBearerAuthenticationConverter);
        bearerAuthenticationFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/api/**"));
        return bearerAuthenticationFilter;
    }
}
