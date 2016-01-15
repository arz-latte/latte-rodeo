package at.arz.latte.rodeo.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ErrorDetail {

	private String message;
	private String detail;

	protected ErrorDetail() {
		// tool constructor
	}

	public ErrorDetail(String message, String detail) {
		this.message = message;
		this.detail = detail;
	}

	public String getMessage() {
		return message;
	}

	public String getDetail() {
		return detail;
	}
}
