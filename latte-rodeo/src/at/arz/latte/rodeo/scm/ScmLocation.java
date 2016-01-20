package at.arz.latte.rodeo.scm;

import java.io.Serializable;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class ScmLocation
		implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlValue
	private String value;

	ScmLocation() {
		// tool constructor;
	}

	public ScmLocation(String value) {
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
		ScmLocation other = (ScmLocation) obj;
		return Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		return value;
	}

	public static ScmLocation fromString(String location) {
		return new ScmLocation(location);
	}

}
