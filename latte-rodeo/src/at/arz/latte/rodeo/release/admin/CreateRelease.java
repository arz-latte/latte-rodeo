package at.arz.latte.rodeo.release.admin;

import java.util.Objects;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import at.arz.latte.rodeo.api.RodeoCommand;
import at.arz.latte.rodeo.infrastructure.RodeoModel;
import at.arz.latte.rodeo.release.Release;
import at.arz.latte.rodeo.release.ReleaseName;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateRelease
		implements RodeoCommand<Long> {

	@NotNull
	private ReleaseName releaseName;

	@XmlTransient
	@Inject
	private RodeoModel model;

	CreateRelease() {
		// tool constructor
	}

	public CreateRelease(ReleaseName releaseName) {
		Objects.requireNonNull(releaseName, "releaseName required");
		this.releaseName = releaseName;

	}

	@Override
	public Long execute() {
		Release release = new Release(releaseName);
		model.create(release);
		return release.getId();
	}

	public ReleaseName getReleaseName() {
		return releaseName;
	}
}
