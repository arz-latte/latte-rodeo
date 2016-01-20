package at.arz.latte.rodeo.scm;

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

import at.arz.latte.rodeo.infrastructure.EventDispatcher;
import at.arz.latte.rodeo.scm.admin.ScmCreated;

@Entity
@Table(name = "SCM_REPOSITORIES")
@NamedQueries({ @NamedQuery(name = Scm.FIND_ALL, query = "select o from Scm o "),
				@NamedQuery(name = Scm.FIND_BY_LOCATION, query = "select o from Scm o where o.location=:location"),
				@NamedQuery(name = Scm.FIND_BY_LOCATION_OR_NAME,
							query = "select o from Scm o where o.location=:location or o.name=:name"), })
public class Scm {

	public static final String FIND_ALL = "Scm.findAll";
	public static final String FIND_BY_LOCATION = "Scm.findByLocation";
	public static final String FIND_BY_LOCATION_OR_NAME = "Scm.findByLocationOrName";

	@Id
	@Column(name = "OID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQUENCES")
	private Long id;

	@Persistent
	@Externalizer("toString")
	@Column(name = "SCM_LOCATION", unique = true, nullable = false)
	private ScmLocation location;

	@Persistent
	@Externalizer("toString")
	@Column(name = "SCM_NAME", unique = true, nullable = false)
	private ScmName name;

	@Persistent
	@Externalizer("toString")
	@Column(name = "SCM_TYPE")
	private ScmType type;

	@Persistent
	@Externalizer("toString")
	@Column(name = "SCM_USER")
	private ScmUserId userId;

	protected Scm() {
		// jpa constructor
	}

	public Scm(ScmName name, ScmLocation location, ScmType type, ScmUserId userId) {
		Objects.requireNonNull(name, "name required");
		Objects.requireNonNull(location, "location required");
		Objects.requireNonNull(type, "type required");
		Objects.requireNonNull(userId, "userId required");
		this.name = name;
		this.location = location;
		this.type = type;
		this.userId = userId;
		EventDispatcher.notify(new ScmCreated(location));
	}

	public Long getId() {
		return id;
	}

	public ScmLocation getLocation() {
		return location;
	}

	public ScmType getType() {
		return type;
	}

	public ScmUserId getUserId() {
		return userId;
	}

	public ScmName getName() {
		return name;
	}

}
