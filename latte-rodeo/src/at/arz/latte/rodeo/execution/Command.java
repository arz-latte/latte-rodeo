package at.arz.latte.rodeo.execution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Command {

	private Map<String, String> environment;
	private String commandLine;

	public Command(String commandLine) {
		this.commandLine = commandLine;
		environment = new HashMap<String, String>(System.getenv());
	}

	public void setEnvironmentVariable(String name, String value) {
		environment.put(name, value);
	}

	public String getEnvironmentVariable(String name) {
		return environment.get(name);
	}

	public void execute(BatchJobProcessor processor) {
		String[] environmentVariables = buildEnvironmentEntries();
		processor.setEnvironmentVariables(environmentVariables);
		// processor.execute(Workspace.buildCommandLine(commandLine));
	}

	private String[] buildEnvironmentEntries() {
		ArrayList<String> list = new ArrayList<String>();
		for (Entry<String, String> entry : environment.entrySet()) {
			list.add(entry.getKey() + "=" + entry.getValue());
		}
		return list.toArray(new String[list.size()]);
	}
}
