package pe.marcolopez.apis.konsula.kambio.services.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import pe.marcolopez.apis.konsula.kambio.clients.EATipoCambioService;
import pe.marcolopez.apis.konsula.kambio.entities.ConsultaLogEntity;
import pe.marcolopez.apis.konsula.kambio.exceptions.DailyQuotaExceededException;
import pe.marcolopez.apis.konsula.kambio.repositories.ConsultaLogRepository;
import pe.marcolopez.apis.konsula.kambio.resources.response.TipoCambioResponse;
import pe.marcolopez.apis.konsula.kambio.services.ConsultaLogService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static pe.marcolopez.apis.konsula.kambio.utils.ConstantsUtil.LIMA;
import static pe.marcolopez.apis.konsula.kambio.utils.ConstantsUtil.MAX_CONSULTAS_DIARIAS;

@ApplicationScoped
public class ConsultaLogServiceImpl implements ConsultaLogService {

	@Inject
	ConsultaLogRepository consultaLogRepository;

	@Inject
	EATipoCambioService tipoCambioService;

	@Override
	@Transactional
	public TipoCambioResponse search(String dni, LocalDate fecha) {
		long used = consultaLogRepository.countByDniAndDate(dni, fecha);

		return Optional.of(used)
				.filter(count -> count < MAX_CONSULTAS_DIARIAS)
				.map(count -> tipoCambioService.getTipoCambio())
				.map(tipoCambio -> {
					var sunat = tipoCambio.sunat();
					var compra = tipoCambio.compra();
					var venta = tipoCambio.venta();

					consultaLogRepository.persist(
							ConsultaLogEntity.builder()
									.dni(dni)
									.fecha(LocalDateTime.now(LIMA))
									.sunat(sunat)
									.compra(compra)
									.venta(venta)
									.build()
					);

					return TipoCambioResponse.builder()
							.sunat(sunat)
							.compra(compra)
							.venta(venta)
							.build();
				})
				.orElseThrow(() -> new DailyQuotaExceededException(dni, fecha, used, MAX_CONSULTAS_DIARIAS));
	}
}
