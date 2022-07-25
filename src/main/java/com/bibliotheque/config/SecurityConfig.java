package com.bibliotheque.config;


import com.bibliotheque.repository.CustomCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    private final BibliothequeConfigProperties bibliothequeConfigProperties;

    public SecurityConfig(BibliothequeConfigProperties bibliothequeConfigProperties, CustomCredentialsRepository credentialsRepository) {
        this.bibliothequeConfigProperties = bibliothequeConfigProperties;
        this.credentialsRepository = credentialsRepository;
    }


    /*
     *
     * A custom UserDetailsService to provide quick user rights for Spring Security,
     * more formal implementations may be added as separated files and annotated as
     * a Spring stereotype.
     *
     * @return MapReactiveUserDetailsService an InMemory implementation of user details
     */
    @Bean
    public MapReactiveUserDetailsService userDetailsRepository() {
        UserDetails user = User.withUsername("bibliotheque")
                .password(
                        passwordEncoder().encode(
                                "Gigi&Titi"
                        )
                )
                .roles("ADMIN")
                .build();
        return new MapReactiveUserDetailsService(user);
    }


    //TODO
    @Autowired
    private CustomCredentialsRepository credentialsRepository;

  /*  public Mono findByLogin(String login) {
        return credentialsRepository.findByLogin(login)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new LoginException("login Not Found"))));
    }*/

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable().cors(corsSpec -> corsSpec.configurationSource(
                        corsMediaSpotConfigurationSource()
                )).authorizeExchange()
                .matchers(new PathPatternParserServerWebExchangeMatcher("/**", HttpMethod.OPTIONS)).permitAll()
                .pathMatchers("/").permitAll()
                .anyExchange().authenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin().disable()
                .build();
    }

    @Bean
    UrlBasedCorsConfigurationSource corsMediaSpotConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        //todo goOd uri here
        configuration.setAllowedOrigins(Arrays.asList("*"));

        configuration.setAllowedHeaders(Arrays.asList("*"));

        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

/*    @Bean
    CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Arrays.asList("http://allowed-origin.com"));
        corsConfig.setMaxAge(8000L);
        corsConfig.addAllowedMethod("POST");
        corsConfig.addAllowedMethod("GET");
        corsConfig.addAllowedHeader("Baeldung-Allowed");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return new CorsWebFilter(source);
    }*/

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
