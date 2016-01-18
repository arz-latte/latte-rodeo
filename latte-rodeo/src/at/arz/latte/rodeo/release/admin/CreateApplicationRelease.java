package at.arz.latte.rodeo.release.admin;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import at.arz.latte.rodeo.api.RodeoCommand;
import at.arz.latte.rodeo.infrastructure.RodeoModel;
import at.arz.latte.rodeo.release.Application;
import at.arz.latte.rodeo.release.ApplicationName;
import at.arz.latte.rodeo.release.ApplicationRelease;
import at.arz.latte.rodeo.release.Release;
import at.arz.latte.rodeo.release.ReleaseName;
import at.arz.latte.rodeo.release.restapi.FindApplicationByNameQuery;
import at.arz.latte.rodeo.release.restapi.FindReleaseByNameQuery;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateApplicationRelease
		implements RodeoCommand<Long> {

	@NotNull
	private ApplicationName applicationName;
	@NotNull
	private ReleaseName releaseName;

	@XmlTransient
	@Inject
	private RodeoModel model;

	CreateApplicationRelease() {
		// tool constructor
	}

	public CreateApplicationRelease(ReleaseName releaseName, ApplicationName applicationName) {
		this.releaseName = releaseName;
		this.applicationName = applicationName;
	}

	@Override
	public Long execute() {
		ApplicationRelease applicationRelease = new ApplicationRelease(getRelease(), getApplication());
		model.create(applicationRelease);
		return applicationRelease.getId();
	}

	private Application getApplication() {
		FindApplicationByNameQuery query = new FindApplicationByNameQuery();
		query.setApplicationName(applicationName);
		Application application = model.query(query);
		return application;
	}

	private Release getRelease() {
		FindReleaseByNameQuery query = new FindReleaseByNameQuery();
		query.setReleaseName(releaseName);
		Release release = model.query(query);
		return release;
	}

	public ReleaseName getReleaseName() {
		return releaseName;
	}

	public ApplicationName getApplicationName() {
		return applicationName;
	}
}
