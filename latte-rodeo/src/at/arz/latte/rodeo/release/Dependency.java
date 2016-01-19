package at.arz.latte.rodeo.release;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: ModuleDependency
 *
 */
@Entity
@Table(name = "MODULE_REVISION_DEPENDENCIES")
@IdClass(DependencyPrimaryKey.class)
public class Dependency
		implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	private Revision source;

	@Id
	@ManyToOne
	private Revision destination;

	private boolean override;

	private String conf;

	protected Dependency() {
		// jpa constructor
	}

	public Dependency(Revision source, Revision destination, String conf, boolean override) {
		this.source = source;
		this.destination = destination;
		this.conf = conf;
		this.override = override;
	}

	public Revision getSource() {
		return source;
	}

	public String getConf() {
		return conf;
	}

	public boolean isOverride() {
		return override;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destination == null) ? 0 : destination.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
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
		Dependency other = (Dependency) obj;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		return true;
	}

}