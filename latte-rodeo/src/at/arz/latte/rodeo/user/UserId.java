package at.arz.latte.rodeo.user;

import java.io.Serializable;
import java.util.Objects;

import javax.xml.bind.annotation.XmlValue;

public class UserId
		implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final UserId UNKNOWN = new UserId("UNKNOWN");

	@XmlValue
	private String value;

	UserId() {
		// tool constructor;
	}

	public UserId(String value) {
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
		UserId other = (UserId) obj;
		return Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		return value;
	}
}
