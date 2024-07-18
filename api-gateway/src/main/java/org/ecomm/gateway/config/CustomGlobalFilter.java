package org.ecomm.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(1)
public class CustomGlobalFilter implements GlobalFilter {

    @Value("${jwt.skiptranslation}")
    private String skipTranslation;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String path = exchange.getRequest().getURI().getPath();
//
//        // Skip token validation for paths starting with /public
//        if (path.startsWith(skipTranslation)) {
//            exchange.getRequest().mutate().headers(httpHeaders -> httpHeaders.remove(HttpHeaders.AUTHORIZATION)).build();
//            return chain.filter(exchange);
//        }

        // For other paths, continue with the chain
        return chain.filter(exchange);
    }
}
