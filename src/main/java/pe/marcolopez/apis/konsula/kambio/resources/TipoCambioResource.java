package pe.marcolopez.apis.konsula.kambio.resources;

import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.RestQuery;
import pe.marcolopez.apis.konsula.kambio.resources.query.TipoCambioQuery;
import pe.marcolopez.apis.konsula.kambio.services.ConsultaLogService;
import java.time.LocalDate;

import static pe.marcolopez.apis.konsula.kambio.utils.ConstantsUtil.LIMA;

@Slf4j
@Path("api/v1/tipo-cambio")
@Produces(MediaType.APPLICATION_JSON)
public class TipoCambioResource {

	@Inject
	ConsultaLogService consultaLogService;

	@GET
	@RunOnVirtualThread
	public Response getTipoCambio(@Valid @BeanParam TipoCambioQuery q) {
		var tipoCambio = consultaLogService.search(q.dni(), LocalDate.now(LIMA));
		return Response.ok(tipoCambio).build();
	}
}
