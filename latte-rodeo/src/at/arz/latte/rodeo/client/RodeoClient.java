package at.arz.latte.rodeo.client;

import java.net.URI;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class RodeoClient {

	private WebResource resource;

	public RodeoClient(URI baseURI, String userName, String password) {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter(userName, password));
		resource = client.resource(baseURI);
	}

	public Builder begin(String path) {
		return resource.path(path).accept("application/xml");
	}

	public Builder begin(String path, String service) {
		return resource.path(path + "/" + service).accept("application/xml");
	}
}
