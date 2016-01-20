package at.arz.latte.rodeo.client;

import java.util.List;

import at.arz.latte.rodeo.scm.ScmLocation;
import at.arz.latte.rodeo.scm.ScmName;
import at.arz.latte.rodeo.scm.ScmType;
import at.arz.latte.rodeo.scm.ScmUserId;
import at.arz.latte.rodeo.scm.admin.CreateScm;
import at.arz.latte.rodeo.scm.restapi.GetScmRepositoriesResult;
import at.arz.latte.rodeo.scm.restapi.ScmResult;

public class ScmClient {

	private static final String API_REPOSITORIES = "api/repositories";
	private RodeoClient client;

	public ScmClient(RodeoClient client) {
		this.client = client;
	}

	public List<ScmResult> listAll() {
		return client.begin(API_REPOSITORIES).get(GetScmRepositoriesResult.class).getScms();
	}

	public ScmResult getScm(ScmName name) {
		return client.begin(API_REPOSITORIES, name.toString()).get(GetScmRepositoriesResult.class).getScms().get(0);
	}

	public void createScm(ScmName name, ScmLocation location, ScmType type, ScmUserId userId) {
		CreateScm command = new CreateScm(name, location, type, userId);
		client.begin(API_REPOSITORIES + "/" + name).put(command);
	}

	public void removeScm(ScmName name) {
		client.begin(API_REPOSITORIES + "/" + name).delete();
	}

}
