package at.arz.latte.rodeo.workspace;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.Semaphore;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import at.arz.latte.rodeo.execution.BatchJobProcessor;
import at.arz.latte.rodeo.execution.Command;
import at.arz.latte.rodeo.execution.SCMProject;
import at.arz.latte.rodeo.workspace.scm.ScmProvider;
import at.arz.latte.rodeo.workspace.scm.ScmProviderFactory;

@Stateless
@LocalBean
public class Workspace {

	private String name;
	private File workspaceDir;
	private Map<String, SCMProject> projects;
	private Semaphore maxParallelJobs;
	private ScmProviderFactory repositoryFactory;
	private Stack<Command> activeCommands;
	private int jobCount;

	public Workspace(String name, File workspaceDir) {
		this.name = name;
		this.workspaceDir = workspaceDir;
		this.repositoryFactory = new ScmProviderFactory(this);
		this.activeCommands = new Stack<Command>();
		projects = new HashMap<String, SCMProject>();
		maxParallelJobs = new Semaphore(2);
	}

	public void addProject(SCMProject project) {
		if (projects.containsKey(project.getName())) {
			throw new IllegalStateException("duplicate project:" + project.getName());
		}
		projects.put(project.getName(), project);
	}

	public void executeCommand(final Command cmd) {
		getFreeJobSlot();
		new Thread(new Runnable() {

			@Override
			public void run() {
				activeCommands.add(cmd);
				FileOutputStream outputStream = createOutputStream();
				try {
					BatchJobProcessor processor = new BatchJobProcessor(Workspace.this, outputStream, System.err);
					cmd.execute(processor);
				} finally {
					close(outputStream);
					activeCommands.remove(cmd);
					maxParallelJobs.release();
				}
			}

			private FileOutputStream createOutputStream() {
				FileOutputStream outputStream;
				try {
					outputStream = new FileOutputStream(new File(getWorkspaceDir(), "output_" + jobCount++ + ".txt"));
				} catch (FileNotFoundException e) {
					throw new IllegalStateException("can't create job output:", e);
				}
				return outputStream;
			}

			private void close(FileOutputStream outputStream) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void shutdown() {
		waitUntilActiveCommandsCompleted();
	}

	public void waitUntilActiveCommandsCompleted() {
		while (!activeCommands.empty()) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				//
			}
		}
	}

	public void initWorkspace() {
		if (!workspaceDir.exists()) {
			workspaceDir.mkdirs();
		}

		for (SCMProject project : projects.values()) {
			File file = new File(workspaceDir, project.getScmModuleName());
			if (file.exists()) {
				continue;
			}
			ScmProvider repository = repositoryFactory.getRepository(project.getScmRepository());
			repository.checkout(project.getScmModuleName(), project.getBranch());
		}
	}

	private void getFreeJobSlot() {
		while (true) {
			try {
				maxParallelJobs.acquire();
				return;
			} catch (InterruptedException e) {

			}
		}
	}

	public void rebuild() {
		waitUntilActiveCommandsCompleted();
		for (SCMProject project : projects.values()) {
			buildProject(project.getScmModuleName());
		}
	}

	private void buildProject(final String projectDir) {
		String commandLine = WorkspaceSettings.get(WorkspaceSettings.ARZBUILD) + " package";
		Command command = new Command(commandLine) {

			@Override
			public void execute(BatchJobProcessor processor) {
				processor.setWorkDirectory(new File(getWorkspaceDir(), projectDir));
				super.execute(processor);
			}
		};
		executeCommand(command);
	}

	public File getWorkspaceDir() {
		return workspaceDir;
	}

	public static String buildCommandLine(String commandLine) {
		String systemSpecificShellCommand = "cmd.exe /C";
		return systemSpecificShellCommand + " " + commandLine;
	}

	public String getName() {
		return name;
	}
}
