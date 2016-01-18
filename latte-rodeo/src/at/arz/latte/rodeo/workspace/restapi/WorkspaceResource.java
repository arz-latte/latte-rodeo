package at.arz.latte.rodeo.workspace.restapi;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import at.arz.latte.rodeo.infrastructure.RodeoSecurity;
import at.arz.latte.rodeo.workspace.Workspace;

@Path("/workspaces")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class WorkspaceResource {

	@Inject
	private Workspace workspace;

	@Inject
	RodeoSecurity security;

	@Path("/")
	@GET
	public Response getRootIndex() {
		return getIndex("/");
	}

	@Path("{path}")
	@GET
	public Response getIndex(@PathParam("path") String path) {
		File workspaceDir = workspace.getWorkspaceDir();
		File subdir = new File(workspaceDir, path);
		if (subdir.exists()) {
			if (subdir.isDirectory()) {
				return Response.status(Status.OK).entity(buildDirList(subdir)).build();
			} else {
				return Response.status(Status.OK)
								.type(MediaType.TEXT_PLAIN)
								.entity("i need to implement the file reader")
								.build();
			}
		}
		return Response.status(Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity(path + " not found").build();

	}

	@Path("{path}")
	@PUT
	public Response updateFile(@PathParam("path") String path, InputStream inputStream) {
		security.assertUserIsAdmin();

		// TODO: implement file update

		return Response.status(Status.NOT_ACCEPTABLE).build();

	}

	private DirListResult buildDirList(File sourceDirectory) {
		List<DirItem> list = buildDirItems(sourceDirectory);
		String path = buildPath(workspace.getWorkspaceDir(), sourceDirectory);
		return new DirListResult(path, list);
	}

	private List<DirItem> buildDirItems(File rootPath) {
		File[] files = rootPath.listFiles();
		ArrayList<DirItem> list = new ArrayList<DirItem>(files.length);
		for (File file : files) {
			DirItem item = new DirItem();
			item.setDirectory(file.isDirectory());
			item.setLastModified(new Date(file.lastModified()));
			item.setSize(file.isDirectory() ? 0 : file.length());
			item.setName(buildPath(rootPath, file));
			list.add(item);
		}
		return list;
	}

	private String buildPath(File directory, File file) {
		return file.toURI().getPath().substring(directory.toURI().getPath().length());
	}

}
