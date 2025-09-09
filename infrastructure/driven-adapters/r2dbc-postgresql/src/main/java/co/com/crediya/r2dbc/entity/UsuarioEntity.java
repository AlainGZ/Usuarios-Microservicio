package co.com.crediya.r2dbc.entity;


import co.com.crediya.model.usuario.Rol;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDate;

@Table("usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioEntity {
	@Id
	@Column("usuario_id")
	private Long id;
	@Column("documento_identidad")
	private Long documentoIdentidad;
	private String nombre;
	private String apellido;
	@Column("fecha_nacimiento")
	private LocalDate fechaNacimiento;
	private String direccion;
	private String telefono;
	@Column("correo_electronico")
	private String correoElectronico;
	@Column("salario_base")
	private Long salarioBase;

	private String clave;
	@Column("rol")
	private Rol rol;
}
