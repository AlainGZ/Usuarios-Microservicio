package co.com.crediya.model.usuario;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Usuario {

	private Long documentoIdentidad;
	private String nombre;
	private String apellido;
	private LocalDate fechaNacimiento;
	private String direccion;
	private String telefono;
	private String correoElectronico;
	private Long salarioBase;

}
