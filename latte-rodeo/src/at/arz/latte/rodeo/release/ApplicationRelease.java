package at.arz.latte.rodeo.release;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import at.arz.latte.rodeo.domain.AbstractEntity;

/**
 * a application release represents the state of one application for one release.
 * 
 * @author mrodler
 * 
 */
@Entity
@Table(	name = "APPLICATION_RELEASES",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "APPLICATION_OID", "RELEASE_OID" }) })
public class ApplicationRelease
		extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "OID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQUENCES")
	private Long id;

	@OneToOne(orphanRemoval = false)
	@Column(name = "APPLICATION_OID", nullable = false)
	private Application application;

	@OneToOne(orphanRemoval = false)
	@Column(name = "RELEASE_OID", nullable = false)
	private Release release;

	protected ApplicationRelease() {
		// jpa constructor
	}

	public ApplicationRelease(Release release, Application application) {
		this.release = release;
		this.application = application;
	}

}
