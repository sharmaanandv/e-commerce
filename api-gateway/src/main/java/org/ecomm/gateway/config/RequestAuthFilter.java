package org.ecomm.gateway.config;

import com.ecomm.comms.config.SecurityContext;
import com.ecomm.comms.dto.UserInfo;
import com.ecomm.comms.exception.EcommException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class RequestAuthFilter extends AbstractGatewayFilterFactory<RequestAuthFilter.Config> {

    @Value("${auth.url}")
    private String authUrl;

    @Value("${jwt.skiptranslation}")
    private String skipTranslation;

    @Autowired
    private WebClient.Builder webClientBuilder;

//    @Autowired
//    private RateLimiter rateLimiter;

    public RequestAuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();

            //TODO Placeholder for implementing rate limiter
            /*if (!rateLimiter.isAllowed(path,"")) {
                return Mono.error(new EcommException("Rate limit exceeded"));
            }*/

            // Skip token verification for public apis
            if (path.startsWith(skipTranslation)) {
                return chain.filter(exchange);
            }
            // For other paths, continue with the chain
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return Mono.error(new EcommException(HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header"));
            }

            String token = authHeader.substring(7);
            return webClientBuilder.build()
                    .get()
                    .uri(authUrl + token)
                    .retrieve()
                    .bodyToMono(UserInfo.class)
                    .flatMap(userInfo -> {
                        exchange.getRequest().mutate().header(SecurityContext.X_USER_ID, String.valueOf(userInfo.getId()));
                        exchange.getRequest().mutate().header(SecurityContext.X_USER_NAME, userInfo.getUsername());
                        exchange.getRequest().mutate().header(SecurityContext.X_USER_ROLE, userInfo.getRoles());
                        exchange.getRequest().mutate().header(SecurityContext.X_USER_TOKEN, authHeader);
                        return chain.filter(exchange);
                    })
                    .onErrorResume(e -> Mono.error(new EcommException(HttpStatus.UNAUTHORIZED, "Invalid Token")));
        };
    }

    public static class Config {
    }

}
