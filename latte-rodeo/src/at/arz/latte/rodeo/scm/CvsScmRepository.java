package at.arz.latte.rodeo.scm;

import at.arz.latte.rodeo.workspace.Workspace;
import at.arz.latte.rodeo.workspace.WorkspaceSettings;
import at.arz.release.execution.Command;

public class CvsScmRepository
		implements ScmRepository {

	private String repositoryURL;
	private Workspace workspace;

	public CvsScmRepository(Workspace workspace, String repositoryURL) {
		this.workspace = workspace;
		this.repositoryURL = repositoryURL;
	}

	public void checkout(String moduleName, String branch) {
		String cvsExecutable = WorkspaceSettings.get(WorkspaceSettings.CVS_EXECUTABLE);
		String commandLine = cvsExecutable + " co -r" + branch + " " + moduleName;
		Command command = new Command(commandLine);
		command.setEnvironmentVariable("CVSROOT", repositoryURL);
		workspace.executeCommand(command);
	}

}
