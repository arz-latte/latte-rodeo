package at.arz.latte.rodeo.release;

import java.util.Collections;
import java.util.List;

public class ModuleStory {

	public Module module;
	
	public static final ModuleStory module(String organisation, String moduleName){
		return new ModuleStory(new Module(new Organisation(organisation), new ModuleName(moduleName)));
	}

	public ModuleStory(Module module) {
		this.module = module;
	}

	public ModuleStory hasRevision(String revision) {
		List<Dependency> dependencies = Collections.emptyList();
		module.addRevision(new Revision(module, revision, dependencies));
		return this;
	}
	
	public RevisionStory revision(String revision){
		return new RevisionStory(module.getRevision(revision));
	}
	
	public class RevisionStory {

		Revision revision;

		RevisionStory(Revision revision){
			this.revision = revision;
		}
		
		public RevisionStory dependsOn(Revision dest, String conf, boolean override){
			revision.createDownstreamDependencyTo(dest, conf, override);
			return this;
		}
		
		

	}

}