package at.arz.latte.rodeo.release;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import at.arz.latte.rodeo.infrastructure.AbstractEntity;

/**
 * provides information about one module-revision.
 * 
 * @author mrodler
 * 
 */
@Entity
@Table(name = "REVISIONS")
public class ModuleRevision
		extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "OID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQUENCES")
	private Long id;

	@ManyToOne
	private Module module;

	private String revision;

	protected ModuleRevision() {
		// jpa constructor
	}

	ModuleRevision(Module module, String revision) {
		this.module = module;
		this.revision = revision;
	}

	public Long getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return "ModuleRevision [module=" + module.getModuleName() + ", revision=" + revision + "]";
	}

	public String getRevision() {
		return revision;
	}

	public Module getModule() {
		return module;
	}

}
