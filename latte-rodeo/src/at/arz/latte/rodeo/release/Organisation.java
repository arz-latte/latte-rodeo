package at.arz.latte.rodeo.release;

import java.io.Serializable;
import java.util.Objects;

import javax.xml.bind.annotation.XmlValue;

public class Organisation
		implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlValue
	private String value;

	Organisation() {
		// tool constructor;
	}

	public Organisation(String value) {
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
		Organisation other = (Organisation) obj;
		return Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		return value;
	}
}
