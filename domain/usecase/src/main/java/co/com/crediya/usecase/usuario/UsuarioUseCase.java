package co.com.crediya.usecase.usuario;

import co.com.crediya.model.usuario.Usuario;
import co.com.crediya.model.usuario.gateways.UsuarioRepository;
import co.com.crediya.usecase.exception.UsuarioException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class UsuarioUseCase {
	private final UsuarioRepository usuarioRepository;



	public Mono<Usuario> ejecutar(Usuario usuario) {

		UsuarioValidator.validar(usuario);


		return usuarioRepository.existByEmail(usuario.getCorreoElectronico())
				.flatMap(existe -> {
					if (existe) {
						throw new UsuarioException(UsuarioConstantes.MENSAJE_CORREO_REPETIDO);
					}
					return usuarioRepository.save(usuario);
				});
	}
}
