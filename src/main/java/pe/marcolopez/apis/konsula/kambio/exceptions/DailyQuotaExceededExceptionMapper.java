package pe.marcolopez.apis.konsula.kambio.exceptions;

import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

import static pe.marcolopez.apis.konsula.kambio.utils.ConstantsUtil.LIMA;

@Provider
public class DailyQuotaExceededExceptionMapper implements ExceptionMapper<DailyQuotaExceededException> {

	@Context
	UriInfo uriInfo;

	@Override
	public Response toResponse(DailyQuotaExceededException ex) {
		// segundos hasta la medianoche local (siguiente día)
		var now = ZonedDateTime.now(LIMA);
		var midnight = now.toLocalDate().plusDays(1).atStartOfDay(LIMA);
		long retryAfterSeconds = Duration.between(now, midnight).getSeconds();

		var body = new ErrorResponse(
				OffsetDateTime.now(LIMA).toString(),
				429,
				"Too Many Requests",
				ex.getMessage(),
				uriInfo != null ? "/" + uriInfo.getPath() : null,
				new Details(ex.getDni(), ex.getFecha().toString(), ex.getUsed(), ex.getLimit())
		);

		return Response.status(429)
				.type(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.RETRY_AFTER, retryAfterSeconds) // estándar HTTP
				.entity(body)
				.build();
	}

	public static record ErrorResponse(
			String timestamp,
			int status,
			String error,
			String message,
			String path,
			Details details
	) {
	}

	public static record Details(
			String dni,
			String fecha,
			long used,
			long limit
	) {
	}
}