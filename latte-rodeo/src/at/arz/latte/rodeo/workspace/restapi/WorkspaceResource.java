package at.arz.latte.rodeo.workspace.restapi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import at.arz.latte.rodeo.infrastructure.RodeoSecurity;
import at.arz.latte.rodeo.rest.XSLTSheet;
import at.arz.latte.rodeo.workspace.Workspace;

@Path("/workspaces")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class WorkspaceResource {

	@Inject
	private Workspace workspace;

	@Inject
	RodeoSecurity security;

	@Context
	private ServletContext context;

	@Context
	private UriInfo uriInfo;

	@Path("/")
	@GET
	@XSLTSheet("workspaces")
	public Response getRootIndex() {
		return getIndex("/");
	}

	@Path("{path : .+}")
	@GET
	@XSLTSheet("workspaces")
	public Response getIndex(@PathParam("path") String path) {
		File workspaceDir = workspace.getWorkspaceDir();
		File subdir = new File(workspaceDir, path);
		if (subdir.exists()) {
			if (subdir.isDirectory()) {
				return Response.status(Status.OK).entity(buildDirList(subdir)).build();
			} else {
				String mimeType = evaluateMimeType(subdir);
				if (MediaType.APPLICATION_OCTET_STREAM.equals(mimeType)) {
					return Response.ok(subdir, mimeType)
									.header("Content-Disposition", "attachment; filename=\"" + subdir.getName() + "\"")
									.build();
				}
				return Response.ok(subdir, mimeType).build();
			}
		}
		return Response.status(Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity(path + " not found").build();

	}

	private String evaluateMimeType(File subdir) {
		String mimeType = context.getMimeType(subdir.getName());
		if (mimeType == null) {
			return MediaType.APPLICATION_OCTET_STREAM;
		}
		return mimeType;
	}

	@Path("{path : .+}")
	@PUT
	public Response uploadFile(@PathParam("path") String path, InputStream inputStream) {
		security.assertUserIsAdmin();

		File workspaceDir = workspace.getWorkspaceDir();
		File destinationFile = new File(workspaceDir, path);

		if (destinationFile.exists()) {
			return Response.status(Status.CONFLICT).build();
		}

		try {
			if (inputStream.available() == 0) {
				createDirectory(destinationFile);
				return Response.ok().build();
			}
			uploadFile(inputStream, destinationFile);
			return Response.ok().build();
		} catch (IOException e) {
			return Response.status(Status.NOT_ACCEPTABLE).build();
		}

	}

	private void uploadFile(InputStream inputStream, File destinationFile) throws IOException {
		destinationFile.getParentFile().mkdirs();
		destinationFile.createNewFile();
		writeStreamToFile(inputStream, destinationFile);
	}

	private void createDirectory(File destinationFile) {
		destinationFile.mkdirs();
	}

	@Path("{path : .+}")
	@POST
	public Response updateFile(@PathParam("path") String path, InputStream inputStream) {
		security.assertUserIsAdmin();
		File workspaceDir = workspace.getWorkspaceDir();
		File destinationFile = new File(workspaceDir, path);

		if (!destinationFile.exists()) {
			return Response.status(Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity(path + " not found").build();
		}

		try {
			writeStreamToFile(inputStream, destinationFile);
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
		File destinationfile = new File(workspaceDir, path);

		if (!destinationfile.exists()) {
			return Response.status(Status.CONFLICT).build();
		}

		if (destinationfile.isFile()) {
			if (destinationfile.delete()) {
				return Response.ok().build();
			}
			return Response.notModified().build();
		}

		if (destinationfile.isDirectory()) {
			deleteDirectory(destinationfile);
		}
		return Response.ok().build();
	}

	private void deleteDirectory(File directory) {
		if(directory.exists()){
			File[] files = directory.listFiles();
			for (File file : files) {
				if(file.isDirectory()){
					deleteDirectory(file);
				}else{
					file.delete();
				}
			}
		}
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
		String parentPath = buildParentPath(workspace.getWorkspaceDir(), sourceDirectory);
		String baseUri = uriInfo.getBaseUri().toString();

		String link = uriInfo.getRequestUri().toString();

		List<DirItem> list = buildDirItems(sourceDirectory, link);
		String path = buildPath(workspace.getWorkspaceDir(), sourceDirectory);

		return new DirListResult(baseUri + "workspaces/" + parentPath, path, list);
	}

	private List<DirItem> buildDirItems(File rootPath, String link) {
		File[] files = rootPath.listFiles();
		ArrayList<DirItem> list = new ArrayList<DirItem>(files.length);
		for (File file : files) {
			DirItem item = new DirItem();
			item.setDirectory(file.isDirectory());
			item.setLastModified(new Date(file.lastModified()));
			item.setSize(file.isDirectory() ? 0 : file.length());
			item.setName(buildPath(rootPath, file));
			item.setLink(link + "/" + file.getName());
			list.add(item);
		}
		return list;
	}

	private String buildPath(File directory, File file) {
		return file.toURI().getPath().substring(directory.toURI().getPath().length());
	}

	private String buildParentPath(File directory, File file) {
		return file.getParentFile().toURI().getPath().substring(directory.toURI().getPath().length());
	}

}
