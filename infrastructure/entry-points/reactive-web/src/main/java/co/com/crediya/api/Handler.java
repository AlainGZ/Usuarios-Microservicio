package co.com.crediya.api;

import co.com.crediya.model.usuario.Usuario;
import co.com.crediya.usecase.exception.UsuarioException;
import co.com.crediya.usecase.usuario.UsuarioConstantes;
import co.com.crediya.usecase.usuario.UsuarioUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
    private  final UsuarioUseCase usuarioUseCase;


    public Mono<ServerResponse> listenSaveUsuario(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Usuario.class)
                .flatMap(usuarioUseCase::ejecutar)
                .flatMap(savedUsuario -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(savedUsuario))
                .onErrorResume(UsuarioException.class, e ->
                        ServerResponse.badRequest()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new ErrorResponse(e.getMessage(), UsuarioConstantes.VALIDAR_DATOS)))
                .onErrorResume(Exception.class, e ->
                        ServerResponse.status(500)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new co.com.crediya.api.ErrorResponse(UsuarioConstantes.ERROR_INTERNO, e.getMessage())));
    }
    public Mono<ServerResponse> findUsuarioByDocumento(ServerRequest request) {
        Long documentoIdentidad = Long.valueOf(request.pathVariable("documentoIdentidad"));

        return usuarioUseCase.findByDocumentoIdentidad(documentoIdentidad)
                .flatMap(existe -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(existe))
                .switchIfEmpty(ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(false));
    }

}
