package com.bibliotheque.security;


import com.bibliotheque.model.Profile;
import com.bibliotheque.model.RequestHeader;
import com.bibliotheque.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class SecurityContextRepository implements ServerSecurityContextRepository {

    private final ObjectMapper mapper;

    public SecurityContextRepository(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Mono<Void> save(ServerWebExchange swe, SecurityContext sc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange swe) {
        ServerHttpRequest request = swe.getRequest();
        RequestHeader requestHeader = null;
        try {
            if (request.getHeaders().getFirst("adminId") != null) {
                requestHeader = Utils.getHeader(request.getHeaders().getFirst("adminId"), mapper);
                if (requestHeader.getAdminId() != null) {
                    UserDetails user = User.withUsername(Utils.toEncodedAdminAccountId(requestHeader))
                            .password(Utils.toEncodedAdminAccountId(requestHeader))
                            .roles(Profile.ADMIN.toString())
                            .build();
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(requestHeader.getAdminId(), requestHeader.getRole(), user.getAuthorities());
                    return Mono.just(new SecurityContextImpl(auth));
                }
            }
            else if (request.getHeaders().getFirst("readerId") != null) {
                requestHeader = Utils.getHeader(request.getHeaders().getFirst("readerId"), mapper);
                if (requestHeader.getAdminId() != null) {
                    UserDetails user = User.withUsername(Utils.toEncodedAdminAccountId(requestHeader))
                            .password(Utils.toEncodedReaderId(requestHeader, requestHeader.getReaderId()))
                            .roles(Profile.MEMBER.toString())
                            .build();
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(requestHeader.getReaderId(), requestHeader.getRole(), user.getAuthorities());
                    return Mono.just(new SecurityContextImpl(auth));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Mono.empty();
    }
}

