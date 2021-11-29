package com.muvraline.usermanager.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.muvraline.commonlibrary.entities.usermanager.model.header.ReqHeader;
import com.muvraline.usermanager.utils.Utils;
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
        //if (request.getURI().getPath().startsWith("/actuator"))
        //    return Mono.empty();
        ReqHeader reqHeader = null;
        try {
            if (request.getHeaders().getFirst("X-context") != null) {
                reqHeader = Utils.getHeader(request.getHeaders().getFirst("X-context"), mapper);
                if (reqHeader.getOpCoAccountId() != null || reqHeader.getTenant().getOpCo() != null || reqHeader.getTenant().getBrand() != null) {
                    UserDetails user = User.withUsername(Utils.toEncodedAccountId(reqHeader))
                            .password(Utils.toEncodedAccountId(reqHeader))
                            .roles(reqHeader.getRole())
                            .build();
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(reqHeader.getOpCoUserId(), reqHeader.getRole(), user.getAuthorities());
                    return Mono.just(new SecurityContextImpl(auth));
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Mono.empty();

    }
}

