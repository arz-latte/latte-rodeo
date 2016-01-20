package at.arz.latte.rodeo.client;

import java.util.List;

import at.arz.latte.rodeo.workspace.restapi.DirItem;
import at.arz.latte.rodeo.workspace.restapi.DirListResult;

public class WorkspaceClient {

	public static final String PROJECTS_PATH = "api/projects";
	public static final String WORKSPACE_PATH = "api/workspaces";

	private RodeoClient client;

	public WorkspaceClient(RodeoClient client) {
		this.client = client;
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
		client.begin(WORKSPACE_PATH + "/" + path).delete();
	}
	

}
