package pe.marcolopez.apis.konsula.kambio.exceptions;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

	@Context
	UriInfo uriInfo;

	@Override
	public Response toResponse(ConstraintViolationException ex) {
		var violations = ex.getConstraintViolations().stream()
				.map(v -> new Violation(
						v.getPropertyPath().toString(),
						v.getMessage()
				))
				.collect(Collectors.toList());

		var body = new ErrorResponse(
				OffsetDateTime.now().toString(),
				Response.Status.BAD_REQUEST.getStatusCode(),
				"Bad Request",
				"Error de validación de parámetros",
				(uriInfo != null ? uriInfo.getPath() : null),
				violations
		);

		return Response.status(Response.Status.BAD_REQUEST)
				.type(MediaType.APPLICATION_JSON)
				.entity(body)
				.build();
	}

	public record Violation(String field, String message) {
	}

	public record ErrorResponse(
			String timestamp,
			int status,
			String error,
			String message,
			String path,
			List<Violation> violations
	) {
	}
}
