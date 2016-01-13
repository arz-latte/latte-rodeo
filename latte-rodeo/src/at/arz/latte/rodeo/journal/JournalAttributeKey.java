package at.arz.latte.rodeo.journal;

import java.io.Serializable;

public class JournalAttributeKey
		implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long entry;
	private String name;
	private int index;

	public JournalAttributeKey() {
		// jpa constructor
	}

	public JournalAttributeKey(Long entry, String attributeName) {
		this.entry = entry;
		this.name = attributeName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((entry == null) ? 0 : entry.hashCode());
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
		JournalAttributeKey other = (JournalAttributeKey) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (entry == null) {
			if (other.entry != null)
				return false;
		} else if (!entry.equals(other.entry))
			return false;
		if (index != other.index)
			return false;
		return true;
	}

}
