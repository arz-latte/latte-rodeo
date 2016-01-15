package at.arz.latte.rodeo.client;

import javax.ws.rs.core.MediaType;

import at.arz.latte.rodeo.api.ErrorDetail;
import at.arz.latte.rodeo.project.command.CreateProject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

public class ProjectAdmin {

	public static void main(String[] args) {
		Client client = Client.create();
		WebResource resource = client.resource("http://localhost:8080/rodeo/api/projects");

		CreateProject createProject = new CreateProject("Sample");

		try {
			resource.accept(MediaType.APPLICATION_XML).put(createProject);
			System.out.println("SUCCESS: " + "created project '" + createProject.getName() + "'");
		} catch (UniformInterfaceException e) {
			ClientResponse response = e.getResponse();
			ErrorDetail detail = response.getEntity(ErrorDetail.class);
			if (detail == null) {
				e.printStackTrace();
			} else {
				processErrorDetail(response, detail);
			}
		}

	}

	private static void processErrorDetail(ClientResponse response, ErrorDetail detail) {
		switch (Status.fromStatusCode(response.getStatus())) {
			case CONFLICT:
				System.out.println("ERROR: " + detail.getMessage() + " " + detail.getDetail());
				break;
			default:
				System.out.println("ERROR: " + detail.getMessage() + " " + detail.getDetail());
		}
	}

}
