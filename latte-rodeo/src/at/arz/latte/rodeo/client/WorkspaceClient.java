package at.arz.latte.rodeo.client;

import java.io.InputStream;
import java.util.List;

import com.sun.jersey.api.client.UniformInterfaceException;

import at.arz.latte.rodeo.scm.ScmLocation;
import at.arz.latte.rodeo.scm.ScmName;
import at.arz.latte.rodeo.scm.restapi.ScmResult;
import at.arz.latte.rodeo.workspace.restapi.DirItem;
import at.arz.latte.rodeo.workspace.restapi.DirListResult;

public class WorkspaceClient {

	public static final String PROJECTS_PATH = "api/projects";
	public static final String WORKSPACE_PATH = "api/workspaces";

	private RodeoClient client;
	private ScmClient scmClient;
	private StepClient stepClient;
	private JobClient jobClient;

	public WorkspaceClient(RodeoClient client) {
		this.client = client;
		this.scmClient = new ScmClient(client);
		this.stepClient = new StepClient(client);
		this.jobClient = new JobClient(client);
	}

	public InputStream downloadFile(String path) {
		return client.begin(WORKSPACE_PATH + "/" + path).get(InputStream.class);
	}

	public void updloadFile(String path, InputStream file) {
		try {
			client.begin(WORKSPACE_PATH + "/" + path).put(file);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void updateFile(String path, InputStream file) {
		try {
			client.begin(WORKSPACE_PATH + "/" + path).post(file);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<DirItem> listWorkspace() {
		return client.begin(WORKSPACE_PATH).get(DirListResult.class).getItems();
	}

	public List<DirItem> listWorkspace(String path) {
		return client.begin(WORKSPACE_PATH + "/" + path).get(DirListResult.class).getItems();
	}

	public void printDirList(List<DirItem> dirList) {
		for (DirItem dirItem : dirList) {
			System.out.println(format(dirItem));
		}
	}

	String format(DirItem dirItem) {
		return String.format("%-20s %2$tY-%2$tm-%2$te %2$tT", dirItem.getName(), dirItem.getLastModified());
	}

	public void remove(String path) {
		try {
			client.begin(WORKSPACE_PATH + "/" + path).delete();
		} catch (UniformInterfaceException e) {
			if (e.getResponse().getStatus() != 409) {
				throw e;
			}
		}
	}
	
	public ScmLocation getScmLocation(ScmName scmName) {
		for (ScmResult result : scmClient.listAll()) {
			if (result.getName().equals(scmName)) {
				return result.getLocation();
			}
		}
		throw new RuntimeException("unknown scm repository:" + scmName);
	}

}
