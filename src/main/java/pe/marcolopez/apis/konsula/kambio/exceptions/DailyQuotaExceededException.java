package pe.marcolopez.apis.konsula.kambio.exceptions;

import java.time.LocalDate;

public class DailyQuotaExceededException extends RuntimeException {
	private final String dni;
	private final LocalDate fecha;
	private final long used;
	private final long limit;

	public DailyQuotaExceededException(String dni, LocalDate fecha, long used, long limit) {
		super("Máximo de consultas diarias alcanzado (" + used + "/" + limit + ") para DNI " + dni + " en " + fecha);
		this.dni = dni;
		this.fecha = fecha;
		this.used = used;
		this.limit = limit;
	}

	public String getDni() { return dni; }
	public LocalDate getFecha() { return fecha; }
	public long getUsed() { return used; }
	public long getLimit() { return limit; }
}