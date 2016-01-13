package at.arz.latte.rodeo;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import at.arz.latte.rodeo.release.restapi.ApplicationResource;
import at.arz.latte.rodeo.release.restapi.ReleaseResource;

@ApplicationPath("/api")
public class RodeoRestAPI extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();
		classes.add(ReleaseResource.class);
		classes.add(ApplicationResource.class);
		return classes;
	}
}
