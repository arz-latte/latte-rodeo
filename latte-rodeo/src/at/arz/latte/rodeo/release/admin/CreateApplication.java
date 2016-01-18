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
import at.arz.latte.rodeo.release.Application;
import at.arz.latte.rodeo.release.ApplicationName;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateApplication
		implements RodeoCommand<Long> {

	@NotNull
	private ApplicationName name;

	@XmlTransient
	@Inject
	private RodeoModel model;

	CreateApplication() {
		// tool constructor
	}

	public CreateApplication(ApplicationName name) {
		Objects.requireNonNull(name, "name required");
		this.name = name;
	}

	@Override
	public Long execute() {
		Application application = new Application(name);
		model.create(application);
		return application.getId();
	}

	public ApplicationName getName() {
		return name;
	}

}
