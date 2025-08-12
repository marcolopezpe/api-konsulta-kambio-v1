package pe.marcolopez.apis.konsula.kambio.resources.response;

import lombok.Builder;
import java.math.BigDecimal;

@Builder
public record TipoCambioResponse(
		BigDecimal sunat,
		BigDecimal compra,
		BigDecimal venta
) {
}
