package at.arz.latte.rodeo.workspace.scm;

import at.arz.latte.rodeo.workspace.Workspace;

public class CvsScmRepository {

	private String repositoryURL;
	private Workspace workspace;

	public CvsScmRepository(Workspace workspace, String repositoryURL) {
		this.workspace = workspace;
		this.repositoryURL = repositoryURL;
	}

	public void checkout(String moduleName, String branch) {
		// String cvsExecutable = WorkspaceSettings.get(WorkspaceSettings.CVS_EXECUTABLE);
		// String commandLine = cvsExecutable + " co -r" + branch + " " + moduleName;
		// Command command = new Command(commandLine);
		// command.setEnvironmentVariable("CVSROOT", repositoryURL);
		// workspace.executeCommand(command);
	}

}
