package co.com.crediya.r2dbc;

import co.com.crediya.r2dbc.entity.UsuarioEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

// TODO: This file is just an example, you should delete or modify it
public interface UsuarioReactiveRepository extends
		ReactiveCrudRepository<UsuarioEntity, String>, ReactiveQueryByExampleExecutor<UsuarioEntity> {

	Mono<UsuarioEntity> findByCorreoElectronico(String correoElectronico);

	Mono<UsuarioEntity> findByDocumentoIdentidad(Long documentoIdentidad);
}
