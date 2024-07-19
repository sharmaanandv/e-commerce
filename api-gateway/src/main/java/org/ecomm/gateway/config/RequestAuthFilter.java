package org.ecomm.gateway.config;

import com.ecomm.comms.exception.EcommException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
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

    public RequestAuthFilter() {
        super(Config.class);
    }

    public static class Config {
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();

            if (path.startsWith(skipTranslation)) {
                return chain.filter(exchange);
            }

            // For other paths, continue with the chain
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return Mono.error(new EcommException("Missing or invalid Authorization header"));
            }

            String token = authHeader.substring(7);

            return webClientBuilder.build()
                    .get()
                    .uri(authUrl + token)
                    .retrieve()
                    .bodyToMono(UserInfo.class)
                    .flatMap(userInfo -> {
                        exchange.getRequest().mutate().header("X-User-Id", userInfo.getId());
                        exchange.getRequest().mutate().header("X-User-Name", userInfo.getUsername());
                        return chain.filter(exchange);
                    })
                    .onErrorResume(e -> Mono.error(new EcommException("Invalid Token")));
        };
    }

    @Getter
    @Setter
    public static class UserInfo {
        private String id;
        private String username;
    }
}
