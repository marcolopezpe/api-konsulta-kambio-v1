package pe.marcolopez.apis.konsula.kambio.clients;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import pe.marcolopez.apis.konsula.kambio.clients.response.TipoCambioExternoResponse;
import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@ApplicationScoped
public class EATipoCambioService {

	@Inject
	@RestClient
	EATipoCambioClient tipoCambioClient;

	@Retry(maxRetries = 2, delay = 50)
	@Fallback(fallbackMethod = "fallbackGetTipoCambio")
	public TipoCambioExternoResponse getTipoCambio() {
		return tipoCambioClient.getTipoCambio();
	}

	TipoCambioExternoResponse fallbackGetTipoCambio() {
		log.warn("FALLBACK: devolviendo fecha '{}' con montos en 0.", LocalDate.now());
		return TipoCambioExternoResponse.builder()
				.fecha(LocalDate.now())
				.sunat(BigDecimal.ZERO)
				.compra(BigDecimal.ZERO)
				.venta(BigDecimal.ZERO)
				.build();
	}
}
