package pe.marcolopez.apis.konsula.kambio.resources;

import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import pe.marcolopez.apis.konsula.kambio.services.ConsultaLogService;
import java.time.LocalDate;

import static pe.marcolopez.apis.konsula.kambio.utils.ConstantsUtil.LIMA;

@Slf4j
@Path("/api/v1/tipo-cambio")
@Produces(MediaType.APPLICATION_JSON)
public class TipoCambioResource {

	@Inject
	ConsultaLogService consultaLogService;

	@GET
	@RunOnVirtualThread
	public Response getTipoCambio(
			@NotBlank(message = "El DNI es obligatorio y no puede estar vacio")
			@QueryParam("dni") String dni) {
		var tipoCambio = consultaLogService.search(dni, LocalDate.now(LIMA));
		return Response.ok(tipoCambio).build();
	}
}
