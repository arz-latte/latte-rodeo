package at.arz.latte.rodeo.scm.admin;

import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import at.arz.latte.rodeo.api.RodeoCommand;
import at.arz.latte.rodeo.scm.Scm;
import at.arz.latte.rodeo.scm.ScmLocation;
import at.arz.latte.rodeo.scm.ScmRepository;
import at.arz.latte.rodeo.scm.ScmType;
import at.arz.latte.rodeo.scm.ScmUserId;

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
	private String name;

	CreateScm() {
		// tool constructor
	}

	public CreateScm(String name, ScmLocation location, ScmType type, ScmUserId userId) {
		this.name = name;
		this.location = location;
		this.type = type;
		this.userId = userId;
	}

	@Override
	public Long execute() {
		List<Scm> scms = repository.findByLocationOrName(location, name);
		if (scms.isEmpty()) {
			Scm scm = new Scm(name, location, type, userId);
			repository.create(scm);
			return scm.getId();
		}
		for (Scm scm : scms) {
			if (name.equals(scm.getName())) {
				throw new ScmNameExists(name);
			}
			if (location.equals(scm.getLocation())) {
				throw new ScmLocationExists(location);
			}
		}
		throw new RuntimeException("found multiple scm entites, but none match name or location");
	}

}
