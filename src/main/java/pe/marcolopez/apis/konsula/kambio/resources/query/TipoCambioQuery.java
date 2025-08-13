package pe.marcolopez.apis.konsula.kambio.resources.query;

import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.QueryParam;
import lombok.Builder;
import org.jboss.resteasy.reactive.RestQuery;

@Builder
public record TipoCambioQuery(

		@QueryParam("dni")
		@NotBlank(message = "El DNI es obligatorio y no puede estar vacio")
		String dni
) {
}
