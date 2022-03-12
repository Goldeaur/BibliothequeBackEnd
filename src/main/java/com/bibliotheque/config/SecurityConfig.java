package com.bibliotheque.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.stereotype.Component;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {


    @Autowired
    private BibliothequeConfigProperties bibliothequeConfigProperties;


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
                        //passwordEncoder().encode(
                        "{noop}Gigi&Titi"
                //)
                )
                .roles("ADMIN")
                .build();
        System.out.println("userDetails ?");
        return new MapReactiveUserDetailsService(user);
    }


    //TODO
   /* @Autowired
    private UserRepository userRepository;

    @Override
    public Mono findByUsername(String username) {
        return userRepository.findByUsername(username).switchIfEmpty(Mono.defer(() -> Mono.error(new UsernameNotFoundException("User Not Found")))).map(User::toAuthUser);
    }*/

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/").permitAll()
                .anyExchange().authenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin().disable()
                .build();
    }
}
