package at.arz.latte.rodeo.execution;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class JobAttribute {

	private String name;
	private String value;

	protected JobAttribute() {
		// tool constructor
	}

	public JobAttribute(String name, String value) {
		Objects.requireNonNull(name, "name required");
		Objects.requireNonNull(value, "value required");
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return name + "=" + value;
	}

}
