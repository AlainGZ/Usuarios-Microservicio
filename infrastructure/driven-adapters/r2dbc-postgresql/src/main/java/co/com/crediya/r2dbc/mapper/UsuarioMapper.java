package co.com.crediya.r2dbc.mapper;

import co.com.crediya.model.usuario.Usuario;
import co.com.crediya.r2dbc.entity.UsuarioEntity;

public class UsuarioMapper {

	public static UsuarioEntity toEntity(Usuario usuario) {
		return UsuarioEntity.builder()
				.documentoIdentidad(usuario.getDocumentoIdentidad())
				.nombre(usuario.getNombre())
				.apellido(usuario.getApellido())
				.fechaNacimiento(usuario.getFechaNacimiento())
				.direccion(usuario.getDireccion())
				.telefono(usuario.getTelefono())
				.correoElectronico(usuario.getCorreoElectronico())
				.salarioBase(usuario.getSalarioBase())
				.clave(usuario.getClave())
				.rol(usuario.getRol())
				.build();
	}

	public static Usuario toModel(UsuarioEntity entity) {
		Usuario usuario = new Usuario();
		usuario.setDocumentoIdentidad(entity.getDocumentoIdentidad());
		usuario.setNombre(entity.getNombre());
		usuario.setApellido(entity.getApellido());
		usuario.setFechaNacimiento(entity.getFechaNacimiento());
		usuario.setDireccion(entity.getDireccion());
		usuario.setTelefono(entity.getTelefono());
		usuario.setCorreoElectronico(entity.getCorreoElectronico());
		usuario.setSalarioBase(entity.getSalarioBase());
		usuario.setClave(entity.getClave());
		usuario.setRol(entity.getRol());
		return usuario;
	}
}
