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
                    path = "/api/v1/usuarios",
                    beanClass = Handler.class,
                    beanMethod = "listenSaveUsuario"
            ),
            @RouterOperation(
                    path = "/api/v1/usuarios/{documentoIdentidad}",
                    beanClass = Handler.class,
                    beanMethod = "findUsuarioByDocumento"
            ),
            @RouterOperation(
                    path = "/api/v1/login",
                    beanClass = Handler.class,
                    beanMethod = "login"
            )
    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
       return route(POST(usuarioPath.getUsuarios()), usuarioHandler::listenSaveUsuario)
               .andRoute(GET("/api/v1/usuarios/{documentoIdentidad}"), usuarioHandler::findUsuarioByDocumento)
               .andRoute(POST("/api/v1/login"), usuarioHandler::login);
           }
}
