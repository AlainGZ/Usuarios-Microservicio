package co.com.crediya.r2dbc;

import co.com.crediya.model.usuario.Usuario;
import co.com.crediya.model.usuario.gateways.UsuarioRepository;
import co.com.crediya.r2dbc.entity.UsuarioEntity;
import co.com.crediya.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class UsuarioRepositoryAdapter extends ReactiveAdapterOperations<
		Usuario,
		UsuarioEntity,
    	String,
		UsuarioReactiveRepository
> implements UsuarioRepository {

	private final UsuarioReactiveRepository repository;

	public UsuarioRepositoryAdapter(UsuarioReactiveRepository repository, ObjectMapper mapper) {

		super(repository, mapper, entity -> mapper.map(entity, Usuario.class));
		this.repository = repository;
	}

	@Override
	public Mono<Usuario> save(Usuario usuario){
		return super.save(usuario);
	}

	@Override
	public Mono<Boolean> existByEmail(String correo) {
		return repository.findByCorreoElectronico(correo)
				.map(u->true)
				.defaultIfEmpty(false);
	}

	@Override
	public Mono<Boolean> existByDocumentoIdentidad(Long documentoIdentidad){
		return repository.findByDocumentoIdentidad(documentoIdentidad)
				.map(u -> true)
				.defaultIfEmpty(false);
	}

	@Override
	public Mono<Usuario> findByEmail(String correo){
		return repository.findByCorreoElectronico(correo)
				.map(entity -> mapper.map(entity, Usuario.class));
	}
}
