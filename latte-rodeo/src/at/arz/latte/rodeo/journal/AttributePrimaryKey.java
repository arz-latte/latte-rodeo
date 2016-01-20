package at.arz.latte.rodeo.journal;

import java.io.Serializable;

public class AttributePrimaryKey
		implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long event;
	private String name;
	private int index;

	public AttributePrimaryKey() {
		// jpa constructor
	}

	public AttributePrimaryKey(Long event, String attributeName) {
		this.event = event;
		this.name = attributeName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + index;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttributePrimaryKey other = (AttributePrimaryKey) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (index != other.index)
			return false;
		return true;
	}

}
