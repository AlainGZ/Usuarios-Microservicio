package co.com.crediya.usecase.usuario;

import co.com.crediya.model.usuario.Usuario;
import co.com.crediya.model.usuario.gateways.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UsuarioUseCaseTest {
	private UsuarioRepository usuarioRepository;
	private UsuarioUseCase usuarioUseCase;

	@BeforeEach
	void setUp() {
		usuarioRepository = Mockito.mock(UsuarioRepository.class);
		usuarioUseCase = new UsuarioUseCase(usuarioRepository);
	}

	private Usuario buildUsuario() {
		Usuario usuario = new Usuario();
		usuario.setNombre("Juan");
		usuario.setApellido("Pérez");
		usuario.setFechaNacimiento(LocalDate.of(2000,1, 1));
		usuario.setDireccion("Calle 123");
		usuario.setTelefono("3001234567");
		usuario.setCorreoElectronico("juan@test.com");
		usuario.setSalarioBase(2000000L);
		return usuario;
	}

	@Test
	void debeGuardarUsuarioCuandoNoExisteCorreo() {
		Usuario usuario = buildUsuario();

		when(usuarioRepository.existByEmail(usuario.getCorreoElectronico())).thenReturn(Mono.just(false));
		when(usuarioRepository.save(any(Usuario.class))).thenReturn(Mono.just(usuario));

		StepVerifier.create(usuarioUseCase.ejecutar(usuario))
				.expectNext(usuario)
				.verifyComplete();

		verify(usuarioRepository).save(usuario);
	}

	@Test
	void debeLanzarErrorCuandoCorreoYaExiste() {
		Usuario usuario = buildUsuario();

		when(usuarioRepository.existByEmail(usuario.getCorreoElectronico())).thenReturn(Mono.just(true));

		StepVerifier.create(usuarioUseCase.ejecutar(usuario))
				.expectErrorMatches(throwable ->
						throwable instanceof IllegalArgumentException &&
								throwable.getMessage().equals("El correo ya está registrado"))
				.verify();

		verify(usuarioRepository, never()).save(any());
	}


}
