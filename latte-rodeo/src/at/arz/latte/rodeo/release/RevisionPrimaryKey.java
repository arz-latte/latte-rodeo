package at.arz.latte.rodeo.release;

import java.io.Serializable;

public class RevisionPrimaryKey
		implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long module;
	private String revision;

	public RevisionPrimaryKey() {
		// jpa constructor
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((revision == null) ? 0 : revision.hashCode());
		result = prime * result + ((module == null) ? 0 : module.hashCode());
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
		RevisionPrimaryKey other = (RevisionPrimaryKey) obj;
		if (revision == null) {
			if (other.revision != null)
				return false;
		} else if (!revision.equals(other.revision))
			return false;
		if (module == null) {
			if (other.module != null)
				return false;
		} else if (!module.equals(other.module))
			return false;
		return true;
	}
}
