package pe.marcolopez.apis.konsula.kambio.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import pe.marcolopez.apis.konsula.kambio.entities.ConsultaLogEntity;
import java.time.LocalDate;
import java.util.UUID;

@ApplicationScoped
public class ConsultaLogRepository implements PanacheRepositoryBase<ConsultaLogEntity, UUID> {

	public long countByDniAndDate(String dni, LocalDate fecha) {
		var start = fecha.atStartOfDay(); // 00:00
		var end = fecha.plusDays(1).atStartOfDay(); // 24:00 (exclusivo)
		return count("dni = ?1 and fecha >= ?2 and fecha < ?3", dni, start, end);
	}
}
