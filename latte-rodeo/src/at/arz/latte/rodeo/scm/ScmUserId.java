package at.arz.latte.rodeo.scm;

import java.util.Objects;

import javax.xml.bind.annotation.XmlValue;


public class ScmUserId {

	@XmlValue
	private String value;

	ScmUserId() {
		// tool constructor;
	}

	public ScmUserId(String value) {
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
		ScmUserId other = (ScmUserId) obj;
		return Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		return value;
	}

}
