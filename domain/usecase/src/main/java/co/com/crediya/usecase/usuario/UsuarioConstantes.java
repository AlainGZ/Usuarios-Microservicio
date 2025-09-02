package co.com.crediya.usecase.usuario;

public final class UsuarioConstantes {

	private UsuarioConstantes(){}

	public static final String MENSAJE_NOMBRES_OBLIGATORIO = "El campo nombres es obligatorio";
	public static final String MENSAJE_APELLIDOS_OBLIGATORIO = "El campo apellidos es obligatorio";
	public static final String MENSAJE_CORREO_OBLIGATORIO = "El correo electrónico es obligatorio";
	public static final String MENSAJE_CORREO_INVALIDO = "Formato de correo electrónico inválido";
	public static final String MENSAJE_SALARIO_INVALIDO = "El salario base debe estar entre 0 y 15,000,000";
	public static final String MENSAJE_CORREO_REPETIDO = "El correo ya esta registrado";

	public static final String ERROR_INTERNO = "Error Interno";
	public static final String VALIDAR_DATOS = "Error de Validacion de Datos";
	public static final String OK = "Usuario registrado exitosamente";

	public static final String TITULO = "CrediYa - Registro de Usuarios";
	public static final String VERSION = "1.0";
	public static final String DESCRIPCION = "Documentación de la API de Usuarios con WebFlux y Clean Architecture";
	public static final String FUNCION_DESCRITA = "Permite registrar un usuario nuevo";

	public static final String ERROR_400 = "400";
	public static final String ERROR_409 = "409";
	public static final String ERROR_200 = "200";
	public static final String ERROR_500 = "500";

	public static final String ROUTER_PATH = "/api/v1/usuarios";
	public static final String ROUTER_BEANMETHOD = "listenSaveUsuario";
	public static final String ROUTER_OPERATIONID = "saveUsuario";


	public static final long SALARIO_MIN = 0L;
	public static final long SALARIO_MAX = 15_000_000L;

}
