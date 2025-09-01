package co.com.crediya.usecase.usuario;

import co.com.crediya.model.usuario.Usuario;
import co.com.crediya.model.usuario.gateways.UsuarioRepository;
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
						return Mono.error(new IllegalArgumentException("El correo ya está registrado"));
					}
					return usuarioRepository.save(usuario);
				});
	}
}
