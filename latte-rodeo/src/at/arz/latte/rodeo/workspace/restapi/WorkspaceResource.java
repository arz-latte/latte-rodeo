package at.arz.latte.rodeo.workspace.restapi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.tomcat.util.http.fileupload.FileUtils;

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

	@Path("{path : .+}")
	@GET
	public Response getIndex(@PathParam("path") String path) {
		File workspaceDir = workspace.getWorkspaceDir();
		File subdir = new File(workspaceDir, path);
		if (subdir.exists()) {
			if (subdir.isDirectory()) {
				return Response.status(Status.OK).entity(buildDirList(subdir)).build();
			} else {
				return Response.ok(subdir, MediaType.APPLICATION_OCTET_STREAM)
								.header("Content-Disposition", "attachment; filename=\"" + subdir.getName() + "\"")
								.build();
			}
		}
		return Response.status(Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity(path + " not found").build();

	}

	@Path("{path : .+}")
	@PUT
	public Response uploadFile(@PathParam("path") String path, InputStream inputStream) {
		security.assertUserIsAdmin();

		File workspaceDir = workspace.getWorkspaceDir();
		File subdir = new File(workspaceDir, path);

		if (subdir.exists()) {
			return Response.status(Status.CONFLICT).build();
		}

		try {
			if (inputStream.available() == 0) {
				subdir.mkdir();
			} else {
				subdir.createNewFile();

				writeStreamToFile(inputStream, subdir);
			}
		} catch (IOException e) {
			return Response.status(Status.NOT_ACCEPTABLE).build();
		}

		return Response.ok().build();
	}

	@Path("{path : .+}")
	@POST
	public Response updateFile(@PathParam("path") String path, InputStream inputStream) {
		security.assertUserIsAdmin();
		File workspaceDir = workspace.getWorkspaceDir();
		File subdir = new File(workspaceDir, path);

		if (!subdir.exists()) {
			Response.status(Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity(path + " not found").build();
		}

		try {
			writeStreamToFile(inputStream, subdir);
		} catch (IOException e) {
			return Response.status(Status.NOT_ACCEPTABLE).build();
		}
		return Response.ok().build();
	}

	@Path("{path : .+}")
	@DELETE
	public Response deleteFileOrDirectoy(@PathParam("path") String path) {
		security.assertUserIsAdmin();
		File workspaceDir = workspace.getWorkspaceDir();
		File subdir = new File(workspaceDir, path);

		if (!subdir.exists()) {
			return Response.status(Status.CONFLICT).build();
		}

		if (subdir.isFile()) {
			if (subdir.delete()) {
				return Response.ok().build();
			}
			return Response.notModified().build();
		}

		if (subdir.isDirectory()) {
			try {
				FileUtils.deleteDirectory(subdir);
			} catch (IOException e) {
				return Response.notModified(e.getMessage()).build();
			}
		}
		return Response.ok().build();
	}

	private void writeStreamToFile(InputStream in, File resource) throws IOException {
		FileOutputStream fout = new FileOutputStream(resource);
		try {
			final byte buffer[] = new byte[1024];
			int rc = 0;
			while (true) {
				rc = in.read(buffer, 0, buffer.length);
				if (rc != -1) {
					fout.write(buffer, 0, rc);
				} else {
					break;
				}
			}

		} finally {
			fout.close();
		}
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
