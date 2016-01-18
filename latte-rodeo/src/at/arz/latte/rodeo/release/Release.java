package at.arz.latte.rodeo.release;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.openjpa.persistence.Externalizer;
import org.apache.openjpa.persistence.Persistent;

import at.arz.latte.rodeo.infrastructure.AbstractEntity;

/**
 * the description of one release.
 * 
 * @author mrodler
 * 
 */
@Entity
@Table(name = "RELEASES")
@NamedQueries({ @NamedQuery(name = Release.SELECT_ALL, query = "select o from Release o "),
				@NamedQuery(name = Release.SELECT_BY_NAME,
							query = "select o from Release o where o.releaseName = :releaseName") })
public class Release
		extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	public static final String SELECT_ALL = "Release.selectAll";
	public static final String SELECT_BY_NAME = "Release.selectByName";

	@Id
	@Column(name = "OID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQUENCES")
	private Long id;

	@Persistent
	@Externalizer("toString")
	@Column(name = "RELEASE_NAME", length = 255, unique = true, nullable = false)
	private ReleaseName releaseName;

	protected Release() {
		// jpa constructor
	}

	public Release(ReleaseName releaseName) {
		Objects.requireNonNull(releaseName, "releaseName required");
		this.releaseName = releaseName;
	}

	public Long getId() {
		return id;
	}

	public ReleaseName getReleaseName() {
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
