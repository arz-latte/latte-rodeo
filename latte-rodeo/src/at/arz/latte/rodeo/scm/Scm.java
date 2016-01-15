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

import at.arz.latte.rodeo.infrastructure.EventDispatcher;
import at.arz.latte.rodeo.scm.admin.ScmCreated;

@Entity
@Table(name = "SCM_REPOSITORIES")
@NamedQueries({ @NamedQuery(name = Scm.FIND_ALL, query = "select o from Scm o "),
				@NamedQuery(name = Scm.FIND_BY_LOCATION, query = "select o from Scm o where o.location=:location") })
public class Scm {

	public static final String FIND_ALL = "Scm.findAll";
	public static final String FIND_BY_LOCATION = "Scm.findByLocation";

	@Id
	@Column(name = "OID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQUENCES")
	private Long id;

	@Column(name = "SCM_LOCATION", unique = true, nullable = false)
	private String location;

	@Column(name = "SCM_NAME", unique = true, nullable = false)
	private String name;

	@Column(name = "SCM_TYPE")
	private String type;

	@Column(name = "SCM_USER")
	private String userId;
	

	protected Scm() {
		// jpa constructor
	}

	public Scm(String name, ScmLocation location, ScmType type, ScmUserId userId) {
		Objects.requireNonNull(name, "name required");
		Objects.requireNonNull(location, "location required");
		Objects.requireNonNull(type, "type required");
		Objects.requireNonNull(userId, "userId required");
		this.name = name;
		this.location = location.toString();
		this.type = type.toString();
		this.userId = userId.toString();
		EventDispatcher.notify(new ScmCreated(location));
	}

	public Long getId() {
		return id;
	}

	public ScmLocation getLocation() {
		return new ScmLocation(location);
	}

	public ScmType getType() {
		return new ScmType(type);
	}

	public ScmUserId getUserId() {
		return new ScmUserId(userId);
	}

}
