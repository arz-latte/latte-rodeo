package at.arz.latte.rodeo.scm.admin;

import java.util.Objects;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import at.arz.latte.rodeo.api.RodeoCommand;
import at.arz.latte.rodeo.scm.Scm;
import at.arz.latte.rodeo.scm.ScmLocation;
import at.arz.latte.rodeo.scm.ScmName;
import at.arz.latte.rodeo.scm.ScmRepository;
import at.arz.latte.rodeo.scm.ScmType;
import at.arz.latte.rodeo.scm.ScmUserId;

/**
 * creates a new scm repository.
 * 
 * a scm repository must have a unique name and a unique location. type and userId are required.
 * 
 * @author mrodler
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateScm
		implements RodeoCommand<Long> {

	@XmlTransient
	@Inject
	private ScmRepository repository;

	@NotNull
	private ScmLocation location;

	@NotNull
	private ScmType type;

	@NotNull
	private ScmUserId userId;

	@NotNull
	private ScmName name;

	CreateScm() {
		// tool constructor
	}

	public CreateScm(ScmName name, ScmLocation location, ScmType type, ScmUserId userId) {
		Objects.requireNonNull(name, "name required");
		Objects.requireNonNull(location, "location required");
		Objects.requireNonNull(type, "type required");
		Objects.requireNonNull(userId, "userId required");
		this.name = name;
		this.location = location;
		this.type = type;
		this.userId = userId;
	}

	@Override
	public Long execute() {
		assertScmIsUnique();
		Scm scm = new Scm(name, location, type, userId);
		repository.create(scm);
		return scm.getId();
	}

	private void assertScmIsUnique() {
		for (Scm scm : repository.findByLocationOrName(location, name)) {
			assertNameIsUnique(scm);
			assertLocationIsUnique(scm);
		}
	}

	private void assertLocationIsUnique(Scm scm) {
		if (location.equals(scm.getLocation())) {
			throw new ScmLocationExists(location);
		}
	}

	private void assertNameIsUnique(Scm scm) {
		if (name.equals(scm.getName())) {
			throw new ScmNameExists(name);
		}
	}

}
