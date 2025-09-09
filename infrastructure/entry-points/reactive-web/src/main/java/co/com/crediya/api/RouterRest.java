package co.com.crediya.api;

import co.com.crediya.api.config.UsuarioPath;
import co.com.crediya.model.usuario.Usuario;
import co.com.crediya.usecase.usuario.UsuarioConstantes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {

    private final UsuarioPath usuarioPath;
    private final Handler usuarioHandler;

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = UsuarioConstantes.ROUTER_PATH,
                    beanClass = Handler.class,
                    beanMethod = UsuarioConstantes.ROUTER_BEANMETHOD,
                    operation = @Operation(
                            operationId = UsuarioConstantes.ROUTER_OPERATIONID,
                            summary = UsuarioConstantes.FUNCION_DESCRITA,
                            requestBody = @RequestBody(
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = Usuario.class))
                            ),
                            responses = {
                                    @ApiResponse(responseCode = UsuarioConstantes.ERROR_200, description = UsuarioConstantes.OK,
                                            content = @Content(schema = @Schema(implementation = Usuario.class))),
                                    @ApiResponse(responseCode = UsuarioConstantes.ERROR_400, description = UsuarioConstantes.VALIDAR_DATOS),
                                    @ApiResponse(responseCode = UsuarioConstantes.ERROR_409, description = UsuarioConstantes.MENSAJE_CORREO_REPETIDO),
                                    @ApiResponse(responseCode = UsuarioConstantes.ERROR_500, description = UsuarioConstantes.ERROR_INTERNO)
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/usuarios/{documentoIdentidad}",
                    beanClass = Handler.class,
                    beanMethod = "findUsuarioByDocumento",
                    operation = @Operation(
                            operationId = "findUsuarioByDocumento",
                            summary = "Buscar usuario por documentoIdentidad",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "true si existe, false si no"),
                                    @ApiResponse(responseCode = "500", description = "Error interno")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/login",
                    beanClass = Handler.class,
                    beanMethod = "login",
                    operation = @Operation(
                            operationId = "login",
                            summary = "Logear con correo y clave",
                            requestBody = @RequestBody(
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = Usuario.class))
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Si se logea con exito"),
                                    @ApiResponse(responseCode = "500", description = "Error interno")
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
       return route(POST(usuarioPath.getUsuarios()), usuarioHandler::listenSaveUsuario)
               .andRoute(GET("/api/v1/usuarios/{documentoIdentidad}"), usuarioHandler::findUsuarioByDocumento)
               .andRoute(POST("/api/v1/login"), usuarioHandler::login);
           }
}
