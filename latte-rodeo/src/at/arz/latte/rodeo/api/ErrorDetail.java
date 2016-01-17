package at.arz.latte.rodeo.api;

import java.net.URI;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ErrorDetail {

	public enum Type {
						DUPLICATE_KEY,
						NOT_FOUND;
	}

	private Type type;

	private URI reference;

	private String message;

	private String detail;

	protected ErrorDetail() {
		// tool constructor
	}

	public ErrorDetail(Type type, String message) {
		this(type, message, null, null);
	}

	public ErrorDetail(Type type, String message, URI reference, String detail) {
		this.type = type;
		this.message = message;
		this.reference = reference;
		this.detail = detail;
	}

	public String getDetail() {
		return detail;
	}

	public String getMessage() {
		return message;
	}

	public URI getReference() {
		return reference;
	}

	public Type getType() {
		return type;
	}

}
