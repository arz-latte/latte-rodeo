package at.arz.latte.rodeo.api;

import java.text.MessageFormat;
import java.util.Locale;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import at.arz.latte.rodeo.api.ErrorDetail.Type;

public class ObjectNotFound
		extends ApiException {

	private static final long serialVersionUID = 1L;

	private Class<?> type;
	private Object key;

	public ObjectNotFound(Class<?> type, Object key) {
		super(type + "[" + key + "]");
		this.type = type;
		this.key = key;
	}

	public Object getKey() {
		return key;
	}

	public Class<?> getType() {
		return type;
	}

	@Override
	public Response createResponse(Locale locale) {
		String entityName = Messages.getString(type.getName(), locale);
		String template = Messages.getString("notFound", locale);
		String message = MessageFormat.format(template, entityName, key);
		return Response.status(Status.NOT_FOUND).entity(new ErrorDetail(Type.NOT_FOUND, message)).build();

	}

}
