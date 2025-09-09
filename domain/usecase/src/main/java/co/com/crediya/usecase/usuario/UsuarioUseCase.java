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

	public Mono<Boolean> findByDocumentoIdentidad(Long documentoIdentidad){
		return usuarioRepository.existByDocumentoIdentidad(documentoIdentidad);
	}

	public Mono<Usuario> login(String correo, String clave){
		return usuarioRepository.findByEmail(correo)
				.switchIfEmpty(Mono.error(new UsuarioException(UsuarioConstantes.USUARIO_NO_ENCONTRADO)))
				.flatMap(usuario -> {
					if (!usuario.getClave().equals(clave)){
						return Mono.error(new UsuarioException(UsuarioConstantes.CLAVE_INCORRECTA));
					}
					return Mono.just(usuario);
				});
	}
}
