package pe.marcolopez.apis.konsula.kambio.clients.response;

import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record TipoCambioExternoResponse(
		LocalDate fecha,
		BigDecimal sunat,
		BigDecimal compra,
		BigDecimal venta
) {
}
