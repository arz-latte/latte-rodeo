package at.arz.latte.rodeo.release;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import at.arz.latte.rodeo.api.AbstractEntity;

/**
 * a component release represents the state of one component for one release.
 * 
 * @author mrodler
 * 
 */
@Entity
@Table(name = "COMPONENT_RELEASES", uniqueConstraints = { @UniqueConstraint(columnNames = { "COMPONENT_OID",
																							"RELEASE_OID" }) })
public class ComponentRelease
		extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "OID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQUENCES")
	private Long id;

	@OneToOne(orphanRemoval = false)
	@Column(name = "COMPONENT_OID", nullable = false)
	private Component component;

	@OneToOne(orphanRemoval = false)
	@Column(name = "RELEASE_OID", nullable = false)
	private Release release;

	protected ComponentRelease() {
		// jpa constructor
	}

	public ComponentRelease(Release release, Component component) {

	}

}
