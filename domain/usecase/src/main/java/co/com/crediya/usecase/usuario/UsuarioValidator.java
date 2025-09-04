package co.com.crediya.usecase.usuario;

import co.com.crediya.model.usuario.Usuario;
import co.com.crediya.usecase.exception.UsuarioException;

public class UsuarioValidator {

	public static void validar(Usuario usuario) {
		validarNombre(usuario.getNombre());
		validarApellido(usuario.getApellido());
		validarCorreo(usuario.getCorreoElectronico());
		validarSalario(usuario.getSalarioBase());
		validarDocumento(usuario.getDocumentoIdentidad());
	}

	private static void validarNombre(String nombres) {
		if (nombres == null || nombres.isBlank()) {
			throw new UsuarioException(UsuarioConstantes.MENSAJE_NOMBRES_OBLIGATORIO);
		}
	}
	private static void validarDocumento(Long documento) {
		if (documento == null || documento < 0) {
			throw new UsuarioException(UsuarioConstantes.MENSAJE_DOCUMENTO_OBLIGATORIO);
		}
	}
	private static void validarApellido(String apellidos) {
		if (apellidos == null || apellidos.isBlank()) {
			throw new UsuarioException(UsuarioConstantes.MENSAJE_APELLIDOS_OBLIGATORIO);
		}
	}

	private static void validarCorreo(String correo) {
		if (correo == null || correo.isBlank()) {
			throw new UsuarioException(UsuarioConstantes.MENSAJE_CORREO_OBLIGATORIO);
		}
		if (!correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
			throw new UsuarioException(UsuarioConstantes.MENSAJE_CORREO_INVALIDO);
		}
	}

	private static void validarSalario(Long salario) {
		if (salario == null || salario < UsuarioConstantes.SALARIO_MIN || salario > UsuarioConstantes.SALARIO_MAX) {
			throw new UsuarioException(UsuarioConstantes.MENSAJE_SALARIO_INVALIDO);
		}
	}
}
