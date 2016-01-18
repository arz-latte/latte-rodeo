package at.arz.latte.rodeo.release.admin;

import java.util.Objects;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import at.arz.latte.rodeo.api.ObjectExists;
import at.arz.latte.rodeo.api.RodeoCommand;
import at.arz.latte.rodeo.infrastructure.RodeoModel;
import at.arz.latte.rodeo.release.Release;
import at.arz.latte.rodeo.release.ReleaseName;
import at.arz.latte.rodeo.release.restapi.FindReleaseByNameQuery;

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
		FindReleaseByNameQuery query = new FindReleaseByNameQuery();
		query.setReleaseName(getReleaseName());
		Release release = model.query(query);
		if (release != null) {
			throw new ObjectExists(Release.class, "releaseName", getReleaseName());
		}

		Release r = new Release(releaseName);
		model.create(r);
		return r.getId();
	}

	public ReleaseName getReleaseName() {
		return releaseName;
	}
}
