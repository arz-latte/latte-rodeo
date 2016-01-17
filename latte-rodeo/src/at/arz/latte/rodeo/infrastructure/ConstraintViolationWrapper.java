package at.arz.latte.rodeo.infrastructure;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationWrapper
		implements ExceptionMapper<ConstraintViolationException> {

	@Override
	public Response toResponse(ConstraintViolationException exception) {
		StringBuffer buffer = new StringBuffer();
		Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
		for (ConstraintViolation<?> violation : violations) {
			buffer.append(violation.getPropertyPath().toString());
			buffer.append(" ");
			buffer.append(violation.getMessage());
			buffer.append(";");
		}
		if (buffer.length() > 0) {
			buffer.setLength(buffer.length() - 1);
		}

		return Response.status(Status.BAD_REQUEST).entity(buffer.toString()).build();
	}
}
