package at.arz.latte.rodeo.api;

import java.util.Locale;

import javax.ws.rs.core.Response;

public abstract class ApiException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	protected ApiException(){
		// tool constructor
	}
	
	public ApiException(String message) {
		super(message);
	}

	public abstract Response createResponse(Locale locale);

}
