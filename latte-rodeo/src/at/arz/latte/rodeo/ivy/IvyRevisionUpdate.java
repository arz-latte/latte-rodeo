package at.arz.latte.rodeo.ivy;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;


public class IvyRevisionUpdate {
	
	private Map<ModuleId, String> dependencies;
	private String moduleRevision;

	public IvyRevisionUpdate(String moduleRevision, Map<ModuleId, String> dependencies) {
		this.moduleRevision = moduleRevision;
		this.dependencies = dependencies;
	}

	/*
	 * info - revision wird mit moduleRevision ersetzt.
	 * 
	 * extends und dependency wird mittels dependency map ersetzt.
	 */
	public void process(InputStream is, OutputStream os) {

	}

}
