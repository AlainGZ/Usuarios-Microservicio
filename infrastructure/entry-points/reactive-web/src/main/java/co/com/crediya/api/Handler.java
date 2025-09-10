package co.com.crediya.api;

import co.com.crediya.api.security.JwtUtil;
import co.com.crediya.api.security.TokenResponse;
import co.com.crediya.model.usuario.Usuario;
import co.com.crediya.usecase.usuario.UsuarioUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            operationId = "saveUsuario",
            summary = "Registrar un nuevo usuario",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = Usuario.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario registrado con éxito",
                            content = @Content(schema = @Schema(implementation = Usuario.class))),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos"),
                    @ApiResponse(responseCode = "401", description = "Token no encontrado o inválido"),
                    @ApiResponse(responseCode = "403", description = "No tienes permisos para registrar usuarios"),
                    @ApiResponse(responseCode = "409", description = "Correo ya registrado"),
                    @ApiResponse(responseCode = "500", description = "Error interno")
            }
    )
    public Mono<ServerResponse> listenSaveUsuario(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(Usuario.class)
                .flatMap(usuarioUseCase::save)
                .flatMap(savedUsuario -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(savedUsuario));
    }

    @Operation(
            operationId = "findUsuarioByDocumento",
            summary = "Buscar usuario por documentoIdentidad",
            responses = {
                    @ApiResponse(responseCode = "200", description = "true si existe, false si no"),
                    @ApiResponse(responseCode = "400", description = "Documento inválido"),
                    @ApiResponse(responseCode = "401", description = "Token no encontrado o inválido"),
                    @ApiResponse(responseCode = "500", description = "Error interno")
            }
    )
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

    @Operation(
            operationId = "login",
            summary = "Logear usuario con correo y clave",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = LoginRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login exitoso, retorna JWT"),
                    @ApiResponse(responseCode = "400", description = "Credenciales inválidas"),
                    @ApiResponse(responseCode = "401", description = "Token no válido"),
                    @ApiResponse(responseCode = "500", description = "Error interno")
            }
    )
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
