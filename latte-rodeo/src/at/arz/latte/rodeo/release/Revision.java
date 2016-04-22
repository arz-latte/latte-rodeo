package at.arz.latte.rodeo.release;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.openjpa.persistence.Externalizer;
import org.apache.openjpa.persistence.Persistent;

import at.arz.latte.rodeo.infrastructure.AbstractEntity;
import at.arz.latte.rodeo.user.User;

/**
 * provides information about one binary module-revision and it's dependencies.
 * 
 * @author mrodler
 */
@Entity
@Table(name = "REVISIONS")
@IdClass(RevisionPrimaryKey.class)
public class Revision
		extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	private Module module;

	@Id
	private String revision;

	@ManyToOne
	@Column(name = "MOUDLE_REVISION_BUILD_BY")
	private User buildBy;

	@Persistent
	@Externalizer("toString")
	@Column(name = "MOUDLE_REVISION_STATUS")
	private ModuleStatus status;

	@Column(name = "MOUDLE_REVISION_MODIFICATION_TIME", nullable = false)
	private Timestamp modificationTime;

	protected Revision() {
		// jpa constructor
	}

	public Revision(Module module, String revision) {
		Objects.requireNonNull(module, "module required");
		Objects.requireNonNull(revision, "revision required");
		this.module = module;
		this.revision = revision;
		this.modificationTime = new Timestamp(System.currentTimeMillis());
	}

	/**
	 * @param modificationTime
	 * @return true if the modificationTime is different.
	 */
	public boolean isModified(Timestamp modificationTime) {
		return !this.modificationTime.equals(modificationTime);
	}

	/**
	 * @return user who build this revision, or null if user is not known.
	 */
	public User getBuildBy() {
		return buildBy;
	}

	public Timestamp getModificationTime() {
		return modificationTime;
	}

	public ModuleStatus getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "Revision [module=" + module + ", revision=" + revision + "]";
	}

	public String getRevision() {
		return revision;
	}

	public Module getModule() {
		return module;
	}

	@Override
	public int hashCode() {
		return Objects.hash(module.hashCode(), revision.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Revision other = (Revision) obj;
		return Objects.equals(module, other.module) && Objects.equals(revision, other.revision);
	}

}
