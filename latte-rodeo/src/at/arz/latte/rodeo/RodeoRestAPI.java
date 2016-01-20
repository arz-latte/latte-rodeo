package at.arz.latte.rodeo;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import at.arz.latte.rodeo.execution.restapi.JobResource;
import at.arz.latte.rodeo.pipeline.restapi.PipelineResource;
import at.arz.latte.rodeo.pipeline.restapi.StepResource;
import at.arz.latte.rodeo.project.restapi.ProjectResource;
import at.arz.latte.rodeo.release.restapi.ApplicationResource;
import at.arz.latte.rodeo.release.restapi.ReleaseResource;
import at.arz.latte.rodeo.scm.restapi.ScmResource;
import at.arz.latte.rodeo.workspace.restapi.WorkspaceResource;

@ApplicationPath("/api")
public class RodeoRestAPI extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();
		classes.add(ReleaseResource.class);
		classes.add(ApplicationResource.class);
		classes.add(ProjectResource.class);
		classes.add(ScmResource.class);
		classes.add(PipelineResource.class);
		classes.add(StepResource.class);
		classes.add(WorkspaceResource.class);
		classes.add(JobResource.class);
		return classes;
	}
}
