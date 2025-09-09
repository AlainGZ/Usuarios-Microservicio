package co.com.crediya.api;

import co.com.crediya.api.security.JwtUtil;
import co.com.crediya.api.security.TokenResponse;
import co.com.crediya.model.usuario.Usuario;
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
                        .bodyValue(savedUsuario));
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
    public Mono<ServerResponse> login(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(LoginRequest.class)
                .flatMap(req -> usuarioUseCase.login(req.getCorreo(), req.getClave()))
                .flatMap(usuario -> {
                    String token = JwtUtil.generateToken(usuario.getCorreoElectronico(), usuario.getRol().name(), usuario.getDocumentoIdentidad());
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(new TokenResponse(token));
                });
    }

}
