package at.arz.latte.rodeo.workspace.scm;

import at.arz.latte.rodeo.workspace.Settings;
import at.arz.latte.rodeo.workspace.Workspace;

public class ScmProvider {

	private Settings settings;
	private Workspace workspace;

	public ScmProvider(Workspace workspace, Settings settings) {
		this.workspace = workspace;
		this.settings = settings;
	}

	public void checkout() {

	}

}
