package co.com.crediya.api.config;

import co.com.crediya.api.ErrorResponse;
import co.com.crediya.usecase.exception.UsuarioException;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class GlobalErrorHandler implements ErrorWebExceptionHandler {
	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		HttpStatus status;
		String mensaje;

		if (ex instanceof UsuarioException) {
			status = HttpStatus.BAD_REQUEST;
			mensaje = ex.getMessage();
		} else {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			mensaje = "Ocurrió un error inesperado";
		}

		ErrorResponse errorResponse = new ErrorResponse(mensaje, status.name());

		exchange.getResponse().setStatusCode(status);
		exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

		byte[] bytes = errorResponse.toString().getBytes();
		return exchange.getResponse()
				.writeWith(Mono.just(exchange.getResponse()
						.bufferFactory()
						.wrap(bytes)));
	}
}
