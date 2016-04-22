package at.arz.latte.rodeo.release;


public class ModuleStory {

	public Module module;
	
	public static final ModuleStory module(String organisation, String moduleName){
		return new ModuleStory(new Module(new Organisation(organisation), new ModuleName(moduleName)));
	}

	public ModuleStory(Module module) {
		this.module = module;
	}

	public ModuleStory hasRevision(String revision) {
		module.addRevision(new Revision(module, revision));
		return this;
	}
	

}