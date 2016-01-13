package at.arz.latte.rodeo.release;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import at.arz.latte.rodeo.api.AbstractEntity;

/**
 * a module is the description one binary artifact and each module can have multiple revisions.
 * 
 * @author mrodler
 * 
 */
@Entity
@Table(	name = "MODULES",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "MODULE_NAME", "MODULE_ORGANISATION" }) })
public class Module
		extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "OID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQUENCES")
	private Long id;

	@Column(name = "MODULE_NAME", nullable = false)
	private String moduleName;

	@Column(name = "MODULE_ORGANISATION", nullable = false)
	private String organisation;

	@OneToMany(mappedBy = "module", orphanRemoval = true)
	private Set<Revision> revisions;

	protected Module() {
		// jpa constructor
	}

	public Module(String moduleName) {
		Objects.requireNonNull(moduleName, "moduleName required");
		this.moduleName = moduleName;
	}

	@Override
	public int hashCode() {
		return moduleName == null ? 0 : moduleName.hashCode();
	}

	public Map<String, Revision> getRevisions() {
		Map<String, Revision> revisionMap = new HashMap<String, Revision>();
		for (Revision revision : revisions) {
			revisionMap.put(revision.getRevision(), revision);
		}
		return Collections.unmodifiableMap(revisionMap);
	}

	public void addRevision(String revision) {
		revisions.add(new Revision(this, revision));
	}

	public String getModuleName() {
		return moduleName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Module other = (Module) obj;
		return Objects.equals(moduleName, other.moduleName);
	}

	Revision getRevision(String revisionName) {
		for (Revision revision : revisions) {
			if (revisionName.equals(revision.getRevision()))
				;
		}
		return null;
	}

}
