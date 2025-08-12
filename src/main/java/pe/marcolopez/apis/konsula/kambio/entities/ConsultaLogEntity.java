package pe.marcolopez.apis.konsula.kambio.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_consulta_log")
public class ConsultaLogEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String dni;

	private BigDecimal sunat;

	private BigDecimal compra;

	private BigDecimal venta;

	private LocalDateTime fecha;
}
