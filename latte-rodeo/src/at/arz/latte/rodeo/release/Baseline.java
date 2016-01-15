package at.arz.latte.rodeo.release;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import at.arz.latte.rodeo.domain.AbstractEntity;

/**
 * A baseline contains all module revisions of one component release.
 * 
 * @author mrodler
 * 
 */
@Entity
@Table(name = "BASELINES")
public class Baseline
		extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "OID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQUENCES")
	private Long id;

	@OneToOne
	@Column(name = "APPLICATION_RELEASE_OID", nullable = false, unique = true)
	private ApplicationRelease applicationRelease;

	private Set<ModuleRevision> moduleRevisions;

	protected Baseline() {
		// jpa constructor
	}

	public Baseline(ApplicationRelease applicationRelease) {
		Objects.requireNonNull(applicationRelease, "applicationRelease required");
		this.applicationRelease = applicationRelease;
		this.moduleRevisions = new HashSet<ModuleRevision>();
	}

	public void addModule(Module module, String revisionName) {
		ModuleRevision revision = module.getRevision(revisionName);
		if (revision != null) {
			moduleRevisions.add(revision);
		}
	}

	public String getModuleRevision(Module module) {
		for (ModuleRevision revision : moduleRevisions) {
			if (module.equals(revision.getModule())) {
				return revision.getRevision();
			}
		}
		return null;
	}
}
