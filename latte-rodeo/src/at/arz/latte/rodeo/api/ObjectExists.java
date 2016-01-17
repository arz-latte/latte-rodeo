package at.arz.latte.rodeo.api;

import java.net.URI;
import java.text.MessageFormat;
import java.util.Locale;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import at.arz.latte.rodeo.api.ErrorDetail.Type;

public class ObjectExists
		extends ApiException {

	private static final long serialVersionUID = 1L;

	private String field;
	private Object value;
	private Class<?> type;

	public ObjectExists(Class<?> type, String field, Object value) {
		super(type + "[" + field + "=" + value + "]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		this.type = type;
		this.field = field;
		this.value = value;
	}

	public Class<?> getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}

	public String getField() {
		return field;
	}

	@Override
	public Response createResponse(Locale locale) {
		return Response.status(Status.CONFLICT).entity(buildErrorResult(locale)).build();
	}

	private ErrorDetail buildErrorResult(Locale locale) {
		// FIXME we need to create the entity reference
		URI reference = URI.create("/" + type.getName() + "/" + field + "/" + value);
		return new ErrorDetail(Type.DUPLICATE_KEY, buildMessage(locale), reference, null);
	}

	private String buildMessage(Locale locale) {
		String entityName = Messages.getString(type.getName(), locale);
		String fieldName = Messages.getString(type.getName() + "." + field, locale);
		String template = Messages.getString("objectExists", locale);
		return MessageFormat.format(template, entityName, fieldName, value);
	}
}
