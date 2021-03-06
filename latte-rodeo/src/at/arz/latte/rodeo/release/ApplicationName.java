package at.arz.latte.rodeo.release;

import java.io.Serializable;
import java.util.Objects;

import javax.xml.bind.annotation.XmlValue;

public class ApplicationName
		implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlValue
	private String value;

	ApplicationName() {
		// tool constructor;
	}

	public ApplicationName(String value) {
		Objects.requireNonNull(value, "value required");
		this.value = value;
	}

	@Override
	public int hashCode() {
		return value == null ? 0 : value.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicationName other = (ApplicationName) obj;
		return Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		return value;
	}
}
