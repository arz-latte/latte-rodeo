package at.arz.latte.rodeo.release;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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

import org.apache.openjpa.persistence.Externalizer;
import org.apache.openjpa.persistence.Persistent;

import at.arz.latte.rodeo.infrastructure.AbstractEntity;

/**
 * a module is the description one binary artifact its build revisions.
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

	@Persistent
	@Externalizer("toString")
	@Column(name = "MODULE_NAME", nullable = false)
	private ModuleName moduleName;

	@Persistent
	@Externalizer("toString")
	@Column(name = "MODULE_ORGANISATION", nullable = false)
	private Organisation organisation;

	@OneToMany(mappedBy = "module", orphanRemoval = true)
	private Set<Revision> revisions;

	@Column(name = "IS_EXTERNAL")
	private boolean external;

	@Column(name = "MODULE_LICENSE")
	private String license;

	@Column(name = "MODULE_DESCRIPTION")
	private String description;

	protected Module() {
		// jpa constructor
	}

	public Module(Organisation organisation, ModuleName moduleName) {
		Objects.requireNonNull(organisation, "organisation required");
		Objects.requireNonNull(moduleName, "moduleName required");
		this.organisation = organisation;
		this.moduleName = moduleName;
		this.revisions = new HashSet<>();
	}

	public void updateDescription(ModuleDescription description) {
		this.description = description.getDescription();
		this.external = description.isExternal();
		this.license = description.getLicense();
	}

	public ModuleDescription getDescription() {
		ModuleDescription description = new ModuleDescription();
		description.setDescription(this.description);
		description.setExternal(this.external);
		description.setLicense(this.license);
		return description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(organisation, moduleName);
	}

	public Map<String, Revision> getRevisions() {
		Map<String, Revision> revisionMap = new HashMap<String, Revision>();
		for (Revision revision : revisions) {
			revisionMap.put(revision.getRevision(), revision);
		}
		return Collections.unmodifiableMap(revisionMap);
	}

	/**
	 * adds a new revision to this module.
	 * 
	 * @param revision the complete revision value.
	 * @param dependencies all direct dependencies of this binary.
	 * @param modificationTime the modification timestamp of the binary.
	 */
	public void addRevision(Revision revision) {
		if (this.equals(revision.getModule())) {
			revisions.add(revision);
		} else {
			throw new UnsupportedOperationException("can't add " + revision + " to " + this);
		}
	}

	public Organisation getOrganisation() {
		return organisation;
	}

	public ModuleName getModuleName() {
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
		return Objects.equals(organisation, other.organisation) && Objects.equals(moduleName, other.moduleName);
	}

	Revision getRevision(String revisionName) {
		for (Revision revision : revisions) {
			if (revisionName.equals(revision.getRevision()))
				return revision;
		}
		return null;
	}

	@Override
	public String toString() {
		return "Module [organisation=" + organisation + ", moduleName=" + moduleName + "]";
	}

}
