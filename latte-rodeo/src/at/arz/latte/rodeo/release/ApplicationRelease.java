package at.arz.latte.rodeo.release;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import at.arz.latte.rodeo.infrastructure.AbstractEntity;

/**
 * a application release represents the state of one application for one release.
 * 
 * @author mrodler
 * 
 */
@Entity
@Table(name = "APPLICATION_RELEASES", uniqueConstraints = { @UniqueConstraint(columnNames = {	"APPLICATION_OID",
																								"RELEASE_OID" }) })
@NamedQueries({ @NamedQuery(name = ApplicationRelease.SELECT_ALL_BY_APPLICATION,
							query = "select o from ApplicationRelease o where o.application = :application"),
				@NamedQuery(name = ApplicationRelease.SELECT_ALL_BY_RELEASE,
							query = "select o from ApplicationRelease o where o.release = :release") })
public class ApplicationRelease
		extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	public static final String SELECT_ALL_BY_APPLICATION = "ApplicationRelease.selectAllByApplication";
	public static final String SELECT_ALL_BY_RELEASE = "ApplicationRelease.selectAllByRelease";

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

	public Long getId() {
		return id;
	}

	public Application getApplication() {
		return application;
	}

	public Release getRelease() {
		return release;
	}
}
