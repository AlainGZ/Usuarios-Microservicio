package co.com.crediya.usecase.usuario;

public final class UsuarioConstantes {

	private UsuarioConstantes(){}

	public static final String MENSAJE_NOMBRES_OBLIGATORIO = "El campo nombres es obligatorio";
	public static final String MENSAJE_APELLIDOS_OBLIGATORIO = "El campo apellidos es obligatorio";
	public static final String MENSAJE_CORREO_OBLIGATORIO = "El correo electrónico es obligatorio";
	public static final String MENSAJE_CORREO_INVALIDO = "Formato de correo electrónico inválido";
	public static final String MENSAJE_SALARIO_INVALIDO = "El salario base debe estar entre 0 y 15,000,000";

	public static final long SALARIO_MIN = 0L;
	public static final long SALARIO_MAX = 15_000_000L;

}
