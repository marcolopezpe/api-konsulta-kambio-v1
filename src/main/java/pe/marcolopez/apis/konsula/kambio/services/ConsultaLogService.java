package pe.marcolopez.apis.konsula.kambio.services;

import pe.marcolopez.apis.konsula.kambio.resources.response.TipoCambioResponse;
import java.time.LocalDate;

public interface ConsultaLogService {

	TipoCambioResponse search(String dni, LocalDate fecha);
}
