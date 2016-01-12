package at.arz.latte.rodeo.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "RELEASES")
@NamedQueries({ @NamedQuery(name = Release.SELECT_ALL, query = "select o from Release o ") })
public class Release implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String SELECT_ALL = "Release.selectAll";

	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "RELEASE_NAME", length = 255, unique = true)
	private String releaseName;

	protected Release() {
		// jpa constructor
	}

	public Release(String releaseName) {
		Objects.requireNonNull(releaseName, "releaseName required");
		this.releaseName = releaseName;
	}

	public Long getId() {
		return id;
	}

	public String getReleaseName() {
		return releaseName;
	}

	@Override
	public int hashCode() {
		return releaseName == null ? 0 : releaseName.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Release other = (Release) obj;
		return releaseName.equals(other.releaseName);
	}

	@Override
	public String toString() {
		return "Release [id=" + id + ", releaseName=" + releaseName + "]";
	}
}
