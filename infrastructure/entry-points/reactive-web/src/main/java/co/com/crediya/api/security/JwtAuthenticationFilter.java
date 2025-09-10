package co.com.crediya.api.security;


import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class JwtAuthenticationFilter implements WebFilter {
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		String path = exchange.getRequest().getURI().getPath();


		if (path.startsWith("/swagger") ||
				path.startsWith("/v3/api-docs") ||
				path.startsWith("/webjars") ||
				path.equals("/api/v1/login")) {
			return chain.filter(exchange);
		}

		String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			DataBuffer buffer = exchange.getResponse()
					.bufferFactory()
					.wrap("{\"mensaje\":\"Token no encontrado\",\"detalle\":\"UNAUTHORIZED\"}".getBytes(StandardCharsets.UTF_8));
			return exchange.getResponse().writeWith(Mono.just(buffer));
		}

		String token = authHeader.substring(7);

		if (!JwtUtil.validateToken(token)) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			DataBuffer buffer = exchange.getResponse()
					.bufferFactory()
					.wrap("{\"mensaje\":\"Token inválido\",\"detalle\":\"UNAUTHORIZED\"}".getBytes(StandardCharsets.UTF_8));
			return exchange.getResponse().writeWith(Mono.just(buffer));
		}

		return chain.filter(exchange);
	}

	private Mono<Void> unauthorized(ServerWebExchange exchange, String mensaje) {
		exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		DataBuffer buffer = exchange.getResponse()
				.bufferFactory()
				.wrap(("{\"mensaje\":\"" + mensaje + "\",\"detalle\":\"UNAUTHORIZED\"}")
						.getBytes(StandardCharsets.UTF_8));
		return exchange.getResponse().writeWith(Mono.just(buffer));
	}

	private Mono<Void> forbidden(ServerWebExchange exchange, String mensaje) {
		exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
		DataBuffer buffer = exchange.getResponse()
				.bufferFactory()
				.wrap(("{\"mensaje\":\"" + mensaje + "\",\"detalle\":\"FORBIDDEN\"}")
						.getBytes(StandardCharsets.UTF_8));
		return exchange.getResponse().writeWith(Mono.just(buffer));
	}
}
