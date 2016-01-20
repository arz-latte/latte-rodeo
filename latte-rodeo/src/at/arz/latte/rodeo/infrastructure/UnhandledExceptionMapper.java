package at.arz.latte.rodeo.infrastructure;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJBException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnhandledExceptionMapper
		implements ExceptionMapper<Exception> {

	private static final Logger log = Logger.getLogger(UnhandledExceptionMapper.class.getName());

	@Override
	public Response toResponse(Exception exception) {
		return buildResponse(unwrap(exception));
	}

	private Response internalServerError() {
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	private Throwable unwrap(Exception exception) {
		if (exception instanceof EJBException) {
			Throwable cause = exception.getCause();
			if (cause == null) {
				return exception;
			}
			return cause;
		}
		return exception;
	}

	private Response buildResponse(Throwable cause) {
		if (cause instanceof ConstraintViolationException) {
			return new ConstraintViolationWrapper().toResponse((ConstraintViolationException) cause);
		}

		if (cause instanceof NullPointerException) {
			logFixme(cause);
			return internalServerError();
		}
		logFixme(cause);
		return internalServerError();
	}

	private void logFixme(Throwable throwable) {
		StackTraceElement element = throwable.getStackTrace()[0];
		if (element.getClassName().startsWith("at.arz")) {
			log.severe("[FIXME] " + throwable.getClass().getSimpleName()
						+ " at "
						+ element.toString()
						+ " (activate log level fine for stacktrace)");
			log.log(Level.FINE, "exception details", throwable);
		} else {
			log.log(Level.INFO, "exception details", throwable);
		}
	}
}
