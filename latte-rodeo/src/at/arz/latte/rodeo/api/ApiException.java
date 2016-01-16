package at.arz.latte.rodeo.api;

import javax.ws.rs.core.Response;

public abstract class ApiException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public ApiException(String message) {
		super(message);
	}

	public abstract Response createResponse();

}
