package pe.marcolopez.apis.konsula.kambio.clients;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import pe.marcolopez.apis.konsula.kambio.clients.response.TipoCambioExternoResponse;

@RegisterRestClient(configKey = "eapi-tipo-cambio")
@Path("/tipo-cambio")
@Produces(MediaType.APPLICATION_JSON)
public interface EATipoCambioClient {

	@GET
	@Path("/today.json")
	TipoCambioExternoResponse getTipoCambio();
}
