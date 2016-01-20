package at.arz.latte.rodeo.client;

import java.util.List;

import at.arz.latte.rodeo.pipeline.StepName;
import at.arz.latte.rodeo.pipeline.admin.CreateCommandLineStep;
import at.arz.latte.rodeo.pipeline.restapi.StepItem;
import at.arz.latte.rodeo.pipeline.restapi.StepItems;

public class StepClient {

	private static final String API_REPOSITORIES = "api/steps";
	private RodeoClient client;

	public StepClient(RodeoClient client) {
		this.client = client;
	}

	public List<StepItem> listAll() {
		return client.begin(API_REPOSITORIES).get(StepItems.class).getSteps();
	}

	public void delete(StepName stepName) {
		client.begin(API_REPOSITORIES + "/" + stepName.toString()).delete();
	}

	public void create(CreateCommandLineStep command) {
		client.begin(API_REPOSITORIES + "/" + command.getName().toString()).put(command);
	}

}
